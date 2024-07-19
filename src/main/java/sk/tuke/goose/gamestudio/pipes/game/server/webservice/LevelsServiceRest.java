package sk.tuke.goose.gamestudio.pipes.game.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.goose.gamestudio.pipes.game.entities.Level;
import sk.tuke.goose.gamestudio.pipes.game.service.LevelsService;

import java.util.List;

@RestController
@RequestMapping("/api/level")
public class LevelsServiceRest {
    private final LevelsService levelsService;

    public LevelsServiceRest(LevelsService levelsService) {
        this.levelsService = levelsService;
    }

    @GetMapping
    public List<Level> getLevels() {
        return levelsService.getLevels();
    }

    @PostMapping
    public void addLevel(@RequestBody Level level) {
        if(level == null){
            return;
        }
        List<Level> levelList = getLevels();
        int id = 0;
        for (Level level1: levelList) {
            if(level1.getLevelId() > id){
                id = level1.getLevelId();
            }
        }
        id++;
        level.setLevelId(id);
        levelsService.addLevel(level);
    }

}
