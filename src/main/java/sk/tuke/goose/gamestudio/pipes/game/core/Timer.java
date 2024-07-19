package sk.tuke.goose.gamestudio.pipes.game.core;

import sk.tuke.goose.gamestudio.pipes.game.handler.GameHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Timer {
    private GameHandler gameHandler;

    public Timer(int time, GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        startTimer(time);
    }

    public void startTimer(int delaySeconds) {
        Executors.newSingleThreadScheduledExecutor().schedule(
                runnable,
                delaySeconds,
                TimeUnit.SECONDS);
    }

    Runnable runnable = new Runnable() {
        @Override public void run() {
            gameHandler.gameLost();
        }
    };
}
