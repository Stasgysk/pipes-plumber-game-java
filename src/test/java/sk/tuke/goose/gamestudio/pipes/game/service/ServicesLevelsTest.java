package sk.tuke.goose.gamestudio.pipes.game.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.tuke.goose.gamestudio.pipes.game.core.Field;
import sk.tuke.goose.gamestudio.pipes.game.entities.Level;
import sk.tuke.goose.gamestudio.pipes.game.handler.GameDifficulty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
@SpringBootTest
public class ServicesLevelsTest {
    @Autowired
    private LevelsService levelsService;
    @Test
    public void reset() {
        levelsService.reset();

        assertEquals(0, levelsService.getLevels().size());
    }


    @Test
    public void addingLevel(){
        int size = levelsService.getLevels().size();
        Field field = new Field(5, 5);
        Level level = new Level(size+1, field.getListOfTiles(), GameDifficulty.EASY, "levelTest", field.getRows(), field.getColumns(), field.getWaterLevel());
        levelsService.addLevel(level);

        List<Level> levelEntities = levelsService.getLevels();
        Level level2 = null;
        assertNotEquals(size, levelEntities.size());
        for (Level levelsToFind : levelEntities){
            if(levelsToFind.getLevelName().equals("levelTest")){
                level2 = levelsToFind;
            }
        }
        assert level2 != null;
        assertEquals("levelTest", level2.getLevelName());
        assertEquals(GameDifficulty.EASY, level2.getGameDifficulty());
        assertEquals(field.getRows(), level2.getRows());
        assertEquals(field.getColumns(), level2.getColumns());
    }
}
