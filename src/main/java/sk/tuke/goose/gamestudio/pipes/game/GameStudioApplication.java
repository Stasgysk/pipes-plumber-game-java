package sk.tuke.goose.gamestudio.pipes.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import sk.tuke.goose.gamestudio.pipes.game.handler.GameHandler;


@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.goose.gamestudio.pipes.game.server.*"))
public class GameStudioApplication implements CommandLineRunner {

    private GameHandler gameHandler;

    @Autowired
    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public static void main(String[] args) {
        //SpringApplication.run(GamestudioApplication.class, args);
        new SpringApplicationBuilder(GameStudioApplication.class).web(WebApplicationType.NONE).run(args);
    }

    /*@Bean
    public CommandLineRunner runner(GameHandler gameHandler) {
        return s -> gameHandler.startGame();
    }*/

    @Override
    public void run(String... args) throws Exception {
        gameHandler.startGame();
    }

}

