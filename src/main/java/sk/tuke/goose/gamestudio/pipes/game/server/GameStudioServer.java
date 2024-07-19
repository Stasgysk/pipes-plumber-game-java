package sk.tuke.goose.gamestudio.pipes.game.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import sk.tuke.goose.gamestudio.pipes.game.service.*;


@SpringBootApplication
@EntityScan(basePackages = "sk.tuke.goose.gamestudio.pipes.game.entities")
@OpenAPIDefinition
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class);
    }
    @Bean
    public ScoresService scoreService() {
        return new ScoresServiceJPA();
    }

    @Bean
    public LevelsService levelsService(){
        return new LevelsServiceJPA();
    }

    @Bean
    public RatingsService ratingsService(){
        return new RatingsServiceJPA();
    }

    @Bean
    public CommentsService commentsService(){
        return new CommentsServiceJPA();
    }

    @Bean
    public MyCustomServices myCustomServices(){
        return new MyCustomServicesJPA();
    }
}
