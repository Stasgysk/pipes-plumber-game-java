package sk.tuke.goose.gamestudio.pipes.game.service;

public class LevelException extends RuntimeException {
    public LevelException(String message) {
        super(message);
    }

    public LevelException(String message, Throwable cause) {
        super(message, cause);
    }
}
