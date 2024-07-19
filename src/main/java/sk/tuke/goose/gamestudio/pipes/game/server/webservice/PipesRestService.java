package sk.tuke.goose.gamestudio.pipes.game.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.goose.gamestudio.pipes.game.core.Field;
import sk.tuke.goose.gamestudio.pipes.game.core.GameTimer;
import sk.tuke.goose.gamestudio.pipes.game.entities.Level;
import sk.tuke.goose.gamestudio.pipes.game.entities.Score;
import sk.tuke.goose.gamestudio.pipes.game.handler.GameDifficulty;
import sk.tuke.goose.gamestudio.pipes.game.service.LevelsService;
import sk.tuke.goose.gamestudio.pipes.game.service.MyCustomServices;

import java.util.List;

@RestController
@RequestMapping("/api/pipes")
public class PipesRestService {
    private Field field;
    private Level level;
    private final LevelsService levelsService;
    private final MyCustomServices myCustomServices;
    private boolean ifEasyMode;
    private static GameTimer timer = null;

    private static synchronized GameTimer getInstance(int time){
        if(timer == null){
            timer = new GameTimer(time);
            timer.start();
        }
        return timer;
    }

    public PipesRestService(LevelsService levelsService, MyCustomServices myCustomServices) {
        this.levelsService = levelsService;
        this.myCustomServices = myCustomServices;
    }

    @GetMapping("/newGame")
    public Field newGame(@RequestParam GameDifficulty gameDifficulty,@RequestParam String levelName){
        if(gameDifficulty == null || levelName == null){
            return null;
        }
        timer = null;
        if(gameDifficulty.equals(GameDifficulty.EASY)){
            timer = getInstance(60);
            field = new Field(5, 5);
        } else if(gameDifficulty.equals(GameDifficulty.MEDIUM)){
            timer = getInstance(60);
            field = new Field(10, 10);
        } else if(gameDifficulty.equals(GameDifficulty.HARD)){
            timer = getInstance(60);
            field = new Field(15, 10);
        }
        level = new Level(1, field.getListOfTiles(), gameDifficulty, levelName, field.getRows(), field.getColumns(), field.getWaterLevel());
        levelsService.addLevel(level);
        return field;
    }

    @GetMapping("/turnPipe")
    public Field turnPipe(@RequestParam int row,@RequestParam int column){
        if(row < 0 || column < 0){
            return null;
        }
        if(row > field.getRows() || column > field.getColumns()){
            return null;
        }
        field.rotatePipe((row - 1) * field.getColumns() + (column - 1), ifEasyMode);
        return field;
    }
    @PostMapping("/setMode")
    public void setMode(@RequestParam boolean ifEasyMode){
        this.ifEasyMode = ifEasyMode;
    }
    @GetMapping("/checkIfWin")
    public boolean isSolved(){
        return field.isSolved();
    }

    @GetMapping("/field")
    public Field getField(){
        return field;
    }

    @GetMapping("/levelId")
    public int getLevelId(){
        return level.getLevelId();
    }

    @GetMapping("/scores")
    public List<Score> getScores(){
        return myCustomServices.getScores("pipes");
    }

    @GetMapping("/timer")
    public int getTimer(){
        if(timer == null){
            return 0;
        }
        return timer.getTimeLeft();
    }

}
