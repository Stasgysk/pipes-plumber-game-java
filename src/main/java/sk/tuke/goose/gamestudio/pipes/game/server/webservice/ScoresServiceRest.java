package sk.tuke.goose.gamestudio.pipes.game.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.goose.gamestudio.pipes.game.entities.Score;
import sk.tuke.goose.gamestudio.pipes.game.service.MyCustomServices;
import sk.tuke.goose.gamestudio.pipes.game.service.ScoresService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoresServiceRest {
    private final ScoresService scoresService;
    private final MyCustomServices myCustomServices;

    public ScoresServiceRest(ScoresService scoresService, MyCustomServices myCustomServices) {
        this.scoresService = scoresService;
        this.myCustomServices = myCustomServices;
    }

    @GetMapping
    public List<Score> getScores() {
        return scoresService.getTopScores("pipes");
    }

    @PostMapping
    public void addScore(@RequestBody Score score) {
        if(score == null){
            return;
        }
        String playerName = score.getPlayerName();
        playerName = playerName.replaceAll("\\s", "");
        score.setPlayerName(playerName);
        List<Score> scoreList = myCustomServices.getScores("pipes");
        for (Score scoreCurr : scoreList) {
            if(scoreCurr.getPlayerName().equals(score.getPlayerName())){
                updateScore(score);
                return;
            }
        }
        int id = 0;
        for (Score scoreCurr : scoreList) {
            if(scoreCurr.getPlayerId() > id){
                id = scoreCurr.getPlayerId();
            }
        }
        id++;
        score.setPlayerId(id);
        scoresService.addScore(score);
    }

    @PutMapping
    public void updateScore(@RequestBody Score score){
        if(score == null){
            return;
        }
        scoresService.updateScore(score);
    }
}
