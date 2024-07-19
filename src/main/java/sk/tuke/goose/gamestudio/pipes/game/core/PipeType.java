package sk.tuke.goose.gamestudio.pipes.game.core;

import java.util.Random;

public enum PipeType {
    STRAIGHT, CORNER, TPIPE;

    public static PipeType generateRandomType() {
        PipeType[] values = PipeType.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
}
