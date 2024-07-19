package sk.tuke.goose.gamestudio.pipes.game.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class FieldTest {

    @Test
    void generateFieldIsSolved(){
        Field field = new Field(15, 10);
        assertFalse(field.isSolved());
    }

    @Test
    void generateFieldWaterLevelCheck(){
        Field field = new Field(15, 10);
        int waterLevel = field.getWaterLevel();
        assertTrue(field.getRows() >= waterLevel, "Error water lever is too high");
        assertTrue(0 < waterLevel, "Error water lever is too low");
    }

    @Test
    void generateFieldTestIfPlaying(){
        Field field = new Field(15, 10);
        assertSame(field.getFieldState(), FieldState.PLAYING, "Error field is not in state \"PLAYING\"");
    }

    @Test
    void generateFieldTestIfPipeSymbolChanged(){
        Field field = new Field(15, 10);
        String tileSymbol = field.getListOfTiles().get(1).getSymbol();
        field.rotatePipe(1, false);
        assertNotEquals(field.getListOfTiles().get(1).getSymbol(), tileSymbol, "Error did not turn the pipe");
    }

    @Test
    void generateFieldTestIfPipeDirectionChanged(){
        Field field = new Field(15, 10);
        TileDirection tileDirection = field.getListOfTiles().get(1).getPipe().getDirection();
        field.rotatePipe(1, false);
        assertNotEquals(field.getListOfTiles().get(1).getPipe().getDirection(), tileDirection, "Error did not turn the pipe");
    }

    @Test
    void generateFieldTestIfPipeSolutionDirectionDidNotChange(){
        Field field = new Field(15, 10);
        TileDirection tileDirection = field.getListOfTiles().get(1).getSolutionDirection();
        field.rotatePipe(1, false);
        assertEquals(field.getListOfTiles().get(1).getSolutionDirection(), tileDirection, "Error changed solutionDirection");
    }

}
