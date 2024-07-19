package sk.tuke.goose.gamestudio.pipes.game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import sk.tuke.goose.gamestudio.pipes.game.service.*;

@Configuration
@ComponentScan("sk.tuke.goose.gamestudio.pipes.game")
public class GameStudioApplicationConfig {
    @Bean
    public LevelsService levelService(){
        return new LevelsServiceRestClient();
    }
    @Bean
    public ScoresService scoresService(){
        return new ScoresServiceRestClient();
    }
    @Bean
    public RatingsService ratingService(){
        return new RatingsServiceRestClient();
    }
    @Bean
    public CommentsService commentService(){
        return new CommentsServiceRestClient();
    }
    @Bean
    public MyCustomServices myCustomServices(){
        return new MyCustomServicesJPA();
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
