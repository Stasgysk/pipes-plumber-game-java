package sk.tuke.goose.gamestudio.pipes.game.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import sk.tuke.goose.gamestudio.pipes.game.colours.ConsoleColours;
import sk.tuke.goose.gamestudio.pipes.game.core.Field;
import sk.tuke.goose.gamestudio.pipes.game.core.FieldState;
import sk.tuke.goose.gamestudio.pipes.game.core.Timer;
import sk.tuke.goose.gamestudio.pipes.game.entities.*;
import sk.tuke.goose.gamestudio.pipes.game.service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static sk.tuke.goose.gamestudio.pipes.game.core.FieldState.*;
import static sk.tuke.goose.gamestudio.pipes.game.handler.GameDifficulty.*;

@Component
public class GameHandler {
    private GameDifficulty gameDifficulty;
    private Field field;
    private final Scanner inputScanner = new Scanner(System.in);
    private final ScoresService scoresService;
    private final LevelsService levelsService;
    private final RatingsService ratingsService;
    private final CommentsService commentsService;
    private final MyCustomServices myCustomServices;
    private Score currentScore;
    private int currentLevelId;
    private List<Level> levelList;
    private boolean ifEasyMode;

    @Autowired
    public GameHandler(@Lazy ScoresService scoresService, @Lazy  LevelsService levelsService, @Lazy  RatingsService ratingsService, @Lazy  CommentsService commentsService, MyCustomServices myCustomServices) {
        this.scoresService = scoresService;
        this.levelsService = levelsService;
        this.ratingsService = ratingsService;
        this.commentsService = commentsService;
        this.myCustomServices = myCustomServices;
    }

//    @Autowired
//    public void setUsersService(UsersService usersService){
//        this.usersService = usersService;
//    }
//    @Autowired
//    public void setLevelsService(LevelsService levelsService) {
//        this.levelsService = levelsService;
//    }

    public void startGame(){
        System.out.println(ConsoleColours.RED_TEXT + "  ____    _       _   _   __  __   ____    _____   ____  ");
        System.out.println(" |  _ \\  | |     | | | | |  \\/  | | __ )  | ____| |  _ \\ ");
        System.out.println(" | |_) | | |     | | | | | |\\/| | |  _ \\  |  _|   | |_) |");
        System.out.println(" |  __/  | |___  | |_| | | |  | | | |_) | | |___  |  _ < ");
        System.out.println(" |_|     |_____|  \\___/  |_|  |_| |____/  |_____| |_| \\_\\" + ConsoleColours.RESET_TEXT);

        //menu choose user\create
        chooseUserToPlay();
        //menu load level\create
        chooseOrCreateLevel();
        gameLoop();
    }

    private void drawGame(){
        System.out.print("  ");
        for (int column = 0; column < field.getColumns(); column++){
            System.out.print(' ');
            System.out.print(column + 1);
        }
        System.out.println();
        for (int row = 0; row < field.getRows(); row++){
            for (int column = 0; column < field.getColumns(); column++){
                if(column == 0){
                    System.out.print(row + 1);
                    if(row <= 8){
                        System.out.print("  ");
                    } else {
                        System.out.print(" ");
                    }
                }
                drawField(row, column);
                drawWaterLevel(row, column);
            }
            System.out.println();
        }
    }

    private void drawField(int row, int column){
        for (Tile tile: field.getListOfTiles()){
            if(tile.getPipe().getRow() == row && tile.getPipe().getColumn() == column ){
                if(ifEasyMode && tile.isSolution()){
                    System.out.print(ConsoleColours.PURPLE_TEXT + tile.getSymbol() + ' ' + ConsoleColours.RESET_TEXT);
                } else {
                    System.out.print(tile.getSymbol() + ' ');
                }
            }
        }
    }

    private void drawWaterLevel(int row, int column){
        if(column == field.getColumns() - 1){
            System.out.print("|");
            if(row >= field.getWaterLevel() - 1){
                System.out.print(ConsoleColours.BLUE_BACKGROUND + " " + ConsoleColours.RESET_TEXT);
            } else {
                System.out.print(" ");
            }
            System.out.print("|");
            if(row == field.getWaterLevel() - 1){
                System.out.print("<-- This is water level, start from here!");
            }
        }
    }

    private void gameLoop() {
        System.out.println("Do you want to play with \"Easy mode\": on? Y/N");
        ifEasyMode = Objects.equals(inputScanner.nextLine(), "Y");
        FieldState fieldState = field.getFieldState();
        setTimerDependingOnDifficulty();
        drawGame();
        //inputThread.start();
        while(fieldState == PLAYING){
            //readUserInput();
            System.out.print("Row: ");
            int row = inputScanner.nextInt();
            if(row > field.getRows() || row < 0){
                System.out.println(ConsoleColours.RED_TEXT + "You entered wrong row!" + ConsoleColours.RESET_TEXT);
                continue;
            }
            System.out.print("Column: ");
            int column = inputScanner.nextInt();
            if(column > field.getColumns() || field.getColumns() < 0){
                System.out.println(ConsoleColours.RED_TEXT + "You entered wrong column!" + ConsoleColours.RESET_TEXT);
                continue;
            }
            field.rotatePipe((row - 1) * field.getColumns() + (column - 1), ifEasyMode);

            drawGame();

            fieldState = field.getFieldState();
        }
        if (field.getFieldState() == SOLVED) {
            showWinScreen();
            currentScore.setScore(currentScore.getScore() + 1);
            scoresService.updateScore(currentScore);
        } else {
            showLoseScreen();
            currentScore.setScore(currentScore.getScore() - 1);
            scoresService.updateScore(currentScore);
        }
        getRatingAndComment();
    }

    private void getRatingAndComment(){
        System.out.print("Please rate the game 0-10: ");
        Rating rating = new Rating(inputScanner.nextInt(), currentScore.getPlayerName(), "pipes");
        if(rating.getRating() > 10){
            rating.setRating(10);
        }
        List<Rating> ratingList = myCustomServices.getRatings("pipes");
        boolean ifFound = false;
        for (Rating r : ratingList){
            if(r.getPlayer().equals(rating.getPlayer())){
                ratingsService.setRating(rating);
                ifFound = true;
            }
        }
        if(!ifFound){
            ratingsService.addRating(rating);
        }
        System.out.print("Please write comment for the game: ");
        InputStreamReader in=new InputStreamReader(System.in);
        BufferedReader read=new BufferedReader(in);
        String commentString;
        try {
            commentString = read.readLine();
            read.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Comment comment = new Comment(commentString, currentScore.getPlayerName(), "pipes");
        if(ifFound){
            commentsService.updateComment(comment);
        }else{
            commentsService.addComment(comment);
        }
    }

    private void setTimerDependingOnDifficulty(){
        switch (gameDifficulty){
            case EASY:
                new Timer(60, this);
            case MEDIUM:
                new Timer(120, this);
            case HARD:
                new Timer(180, this);
        }
    }

//    Thread inputThread = new Thread(new Runnable() {
//        @Override
//        public void run() {
//
//            //Scanner scan = new Scanner(System.in);
//            String input = "";
//            while(field.getFieldState() == PLAYING){
//                //readUserInput();
//                System.out.print("Row: ");
//                int row = inputScanner.nextInt();
//                if(row > rows || row < 0){
//                    System.out.println(ConsoleColours.RED_TEXT + "You entered wrong row!" + ConsoleColours.RESET_TEXT);
//                    continue;
//                }
//                System.out.print("Column: ");
//                int column = inputScanner.nextInt();
//                if(column > columns || columns < 0){
//                    System.out.println(ConsoleColours.RED_TEXT + "You entered wrong column!" + ConsoleColours.RESET_TEXT);
//                    continue;
//                }
//                field.rotatePipe((row - 1) * field.getColumns() + (column - 1), ifEasyMode);
//
//                drawGame();
//            }
//            if(field.getFieldState() == SOLVED){
//                showWinScreen();
//                usersServiceJDBC.updateScore(currentPlayer, currentPlayerScore + 1, currentLevelId);
//            } else {
//                showLoseScreen();
//            }
//        }
//    });


    private void showWinScreen(){
        System.out.println(ConsoleColours.RED_TEXT + "   ____ ___  _   _  ____ ____      _  _____ _   _ _        _  _____ ___ ___  _   _   _   _   _ ");
        System.out.println("  / ___/ _ \\| \\ | |/ ___|  _ \\    / \\|_   _| | | | |      / \\|_   _|_ _/ _ \\| \\ | | | | | | | |");
        System.out.println(" | |  | | | |  \\| | |  _| |_) |  / _ \\ | | | | | | |     / _ \\ | |  | | | | |  \\| | | | | | | |");
        System.out.println(" | |__| |_| | |\\  | |_| |  _ <  / ___ \\| | | |_| | |___ / ___ \\| |  | | |_| | |\\  | |_| |_| |_|");
        System.out.println("  \\____\\___/|_| \\_|\\____|_| \\_\\/_/   \\_\\_|  \\___/|_____/_/   \\_\\_| |___\\___/|_| \\_| (_) (_) (_)");
        System.out.println(" __   _____  _   _  __        _____  _   _   _   _   _ ");
        System.out.println(" \\ \\ / / _ \\| | | | \\ \\      / / _ \\| \\ | | | | | | | |");
        System.out.println("  \\ V / | | | | | |  \\ \\ __ / / | | |  \\| | | | | | | |");
        System.out.println("   | || |_| | |_| |   \\ V  V /| |_| | |\\  | |_| |_| |_|");
        System.out.println("   |_| \\___/ \\___/     \\_/\\_/  \\___/|_| \\_| (_) (_) (_)" + ConsoleColours.RESET_TEXT);
    }

    private void showLoseScreen(){
        System.out.println(ConsoleColours.RED_TEXT + " __   _____  _   _   _____ _    ___ _     _____ ____    _    __");
        System.out.println(" \\ \\ / / _ \\| | | | |  ___/ \\  |_ _| |   | ____|  _ \\  | |  / /");
        System.out.println("  \\ V / | | | | | | | |_ / _ \\  | || |   |  _| | | | | | | | |");
        System.out.println("   | || |_| | |_| | |  _/ ___ \\ | || |___| |___| |_| | |_| | | ");
        System.out.println("   |_| \\___/ \\___/  |_|/_/   \\_\\___|_____|_____|____/  (_) | |");
        System.out.println("                                                            \\_\\" + ConsoleColours.RESET_TEXT);
    }


    private void getDifficultyLevelFromUser(){
        System.out.print("Choose game difficulty (HARD, MEDIUM, EASY): ");
        String difficulty = inputScanner.nextLine();
        switch (difficulty.toUpperCase()) {
            case "HARD" -> setGameDifficulty(HARD);
            case "MEDIUM" -> setGameDifficulty(MEDIUM);
            case "EASY" -> setGameDifficulty(EASY);
            default -> {
                System.out.println("Wrong input! Choose from these HARD/MEDIUM/EASY.");
                getDifficultyLevelFromUser();
            }
        }
    }

    private void chooseUserToPlay(){
        List<Score> scoreList = scoresService.getTopScores("pipes");
        if(scoreList.size() > 0){
            for (int i = 0; i < scoreList.size(); i++){
                for (Score score : scoreList){
                    if(score.getPlayerId() == i+1){
                        System.out.println("Id: " + score.getPlayerId() + ". Name: " + score.getPlayerName() + ". " + "Score: " + score.getScore() + ".");
                    }
                }
            }
            System.out.print("Choose player from above - type id of a player or create new player - type \"c\"\nInput: ");
            String playerInput = inputScanner.nextLine();
            if(playerInput.equalsIgnoreCase("c")){
                createNewPlayer(scoreList, scoreList.size());
            } else {
                for (Score score : scoreList){
                    if(score.getPlayerId() == Integer.parseInt(playerInput)){
                        currentScore = score;
                        return;
                    }
                }
                System.out.println("You chose wrong player ID! Try again!");
                chooseUserToPlay();
            }
        } else {
            createNewPlayer(scoreList, 0);
        }
    }

    private String checkIfPlayerDoesntExist(List<Score> scoreList, String playerInput){
        for (Score score : scoreList){
            if(Objects.equals(score.getPlayerName().toLowerCase(), playerInput.toLowerCase())){
                System.out.println("Sorry this name is already taken!");
                System.out.println("Choose another name: ");
                playerInput = inputScanner.nextLine();
                playerInput = checkIfPlayerDoesntExist(scoreList, playerInput);
                break;
            }
        }
        return playerInput;
    }

    private void createNewPlayer(List<Score> scoreList, int id){
        System.out.print("Input player name: ");
        String playerName = inputScanner.nextLine();
        playerName = checkIfPlayerDoesntExist(scoreList, playerName);
        Score score = new Score(id + 1, playerName, 0, "pipes", 0);
        //List<Player> players = usersService.getPlayers();
        scoresService.addScore(score);
        currentScore = score;
    }

    public GameDifficulty getGameDifficulty(){
        return gameDifficulty;
    }

    void setGameDifficulty(GameDifficulty gameDifficulty){
        this.gameDifficulty = gameDifficulty;
    }


    private void chooseOrCreateLevel(){
        levelList = levelsService.getLevels();
        if(levelList.size() > 0){
            for (int i = 0; i < levelList.size(); i++){
                for (Level level : levelList){
                    if(level.getLevelId() == i+1) {
                        System.out.println("Id: " + level.getLevelId() + ". Name: " + level.getLevelName() + ". Difficulty: " + level.getGameDifficulty() + ".");
                    }
                }
            }
            System.out.println("Choose level by id, or type \"c\" to create one.");
            System.out.print("Input: ");
            String playerInput = inputScanner.nextLine();
            if (!playerInput.equalsIgnoreCase("c")) {
                for (Level level : levelList) {
                    if (level.getLevelId() == Integer.parseInt(playerInput)) {
                        gameDifficulty = level.getGameDifficulty();
                        currentLevelId = level.getLevelId();
                        field = new Field(level.getRows(), level.getColumns(), level.getLevel(), level.getWaterLevel());
                        return;
                    }
                }
                System.out.println("Wrong input! Please Create level!");
            }
        } else {
            System.out.println("Please Create level!");
        }
        getDifficultyLevelFromUser();
        createLevel();
    }

    private void createLevel(){
        if(gameDifficulty == HARD){
            field = new Field(15, 10);
        }else if(gameDifficulty == MEDIUM){
            field = new Field(10, 10);
        }else if(gameDifficulty == EASY){
            field = new Field(5, 5);
        }
        System.out.print("Print level name: ");
        currentLevelId = levelList.size() + 1;
        String playerInput = inputScanner.nextLine();
        playerInput = checkIfLevelDoesntExist(playerInput);
        Level level = new Level(currentLevelId, field.getListOfTiles(), getGameDifficulty(), playerInput, field.getRows(), field.getColumns(), field.getWaterLevel());
        levelsService.addLevel(level);
    }

    private String checkIfLevelDoesntExist(String playerInput){
        for (Level level : levelList){
            if(Objects.equals(level.getLevelName().toLowerCase(), playerInput.toLowerCase())){
                System.out.println("Sorry this level name is already taken!");
                System.out.println("Choose another name: ");
                playerInput = inputScanner.nextLine();
                playerInput = checkIfLevelDoesntExist(playerInput);
                break;
            }
        }
        return playerInput;
    }

    public void gameLost(){
        field.setFieldState(FAILED);
        //inputThread.interrupt();
    }
}
