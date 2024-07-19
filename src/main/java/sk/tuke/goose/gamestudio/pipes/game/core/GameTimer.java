package sk.tuke.goose.gamestudio.pipes.game.core;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private int timeLeft;

    public GameTimer(int durationInSeconds) {
        timer = new Timer();
        timeLeft = durationInSeconds;
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                timeLeft--;
            }
        }, 1000, 1000); // execute every second

        // Stop the timer after the specified duration
        timer.schedule(new TimerTask() {
            public void run() {
                stop();
            }
        }, timeLeft * 1000);
    }

    public void stop() {
        timer.cancel();
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}