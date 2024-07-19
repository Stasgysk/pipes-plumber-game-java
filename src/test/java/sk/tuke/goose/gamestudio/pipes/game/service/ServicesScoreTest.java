package sk.tuke.goose.gamestudio.pipes.game.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import sk.tuke.goose.gamestudio.pipes.game.entities.Score;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServicesScoreTest {
    @Autowired
    private ScoresService scoresService;
    @Test
    public void reset() {
        scoresService.reset();

        assertEquals(0, scoresService.getTopScores("pipes").size());
    }

    @Test
    public void addingPlayer(){
        scoresService.reset();
        scoresService.addScore(new Score(111, "test", 10, "pipes", 10));

        List<Score> scoreList = scoresService.getTopScores("pipes");
        Score score = null;
        //assertNotEquals(0, playerList.size());
        for (Score scoreToFind : scoreList){
            if(scoreToFind.getPlayerName().equals("test")){
                score = scoreToFind;
            }
        }
        assert score != null;
        assertEquals("test", score.getPlayerName());
        assertEquals(10, score.getScore());
    }

    @Test
    public void updatingPLayer(){
        scoresService.reset();
        Score score1 = new Score(12312, "test2", 0, "pipes",0);
        scoresService.addScore(score1);
        score1.setScore(11);
        score1.setLevelId(1);
        scoresService.updateScore(score1);
        Score score = null;
        for (Score scoreToFind : scoresService.getTopScores("pipes")){
            if(scoreToFind.getPlayerName().equals("test2")){
                score = scoreToFind;
            }
        }
        assert score != null;
        assertEquals(11, score.getScore());
    }

    @Configuration
    public static class ContextConfiguration {
    }
}
