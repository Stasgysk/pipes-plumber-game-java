package sk.tuke.goose.gamestudio.pipes.game.core;

import java.util.Random;

public enum TileDirection {
    NORTH, SOUTH, WEST, EAST;

    public static TileDirection generateRandomDirection() {
        TileDirection[] values = TileDirection.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }

    public TileDirection getNextDirection(TileDirection tileDirection) {
        switch (tileDirection) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
        }
        return tileDirection;
    }

}
