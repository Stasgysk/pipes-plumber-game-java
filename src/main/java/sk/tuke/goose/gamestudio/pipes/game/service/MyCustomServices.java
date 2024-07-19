package sk.tuke.goose.gamestudio.pipes.game.service;

import sk.tuke.goose.gamestudio.pipes.game.entities.Rating;
import sk.tuke.goose.gamestudio.pipes.game.entities.Score;

import java.util.List;

public interface MyCustomServices {
    List<Score> getScores(String game);
    List<Rating> getRatings(String game);
}
