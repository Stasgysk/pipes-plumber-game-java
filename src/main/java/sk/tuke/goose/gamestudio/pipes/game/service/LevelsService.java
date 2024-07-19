package sk.tuke.goose.gamestudio.pipes.game.service;

import sk.tuke.goose.gamestudio.pipes.game.entities.Level;

import java.util.List;

public interface LevelsService {
    void addLevel(Level level);
    List<Level> getLevels();
    void reset();
//    public LevelInfo getLevelById(int id);
}
