package sk.tuke.goose.gamestudio.pipes.game.service;

import sk.tuke.goose.gamestudio.pipes.game.entities.Score;

import java.util.List;

public interface ScoresService {

    void addScore(Score score);
    void updateScore(Score score);

    List<Score> getTopScores(String game);
    void reset();

}
