package sk.tuke.goose.gamestudio.pipes.game.service;

import sk.tuke.goose.gamestudio.pipes.game.entities.Rating;

public interface RatingsService {
    void addRating(Rating rating);

    int getRating(String game, String player);
    int getAverageRating(String game);
    void setRating(Rating rating);
    void reset();
}
