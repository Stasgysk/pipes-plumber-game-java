package sk.tuke.goose.gamestudio.pipes.game.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TileDirectionTest {
    @Test
    public void testNextTileDirection(){
        TileDirection tileDirection = TileDirection.generateRandomDirection();

        assertNotEquals(tileDirection, tileDirection.getNextDirection(tileDirection));

        assertEquals(TileDirection.EAST, tileDirection.getNextDirection(TileDirection.NORTH));
        assertEquals(TileDirection.SOUTH, tileDirection.getNextDirection(TileDirection.EAST));
        assertEquals(TileDirection.WEST, tileDirection.getNextDirection(TileDirection.SOUTH));
        assertEquals(TileDirection.NORTH, tileDirection.getNextDirection(TileDirection.WEST));
    }
}
