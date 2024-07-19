package sk.tuke.goose.gamestudio.pipes.game.entities;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import sk.tuke.goose.gamestudio.pipes.game.handler.GameDifficulty;

import java.util.List;
@Entity
public class Level {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "level_id")
    private int levelId;

    @Type(JsonBinaryType.class)
    @Column(name = "level_info")
    private List<Tile> levelInfo;
    @Enumerated
    @Column(name = "level_difficulty")
    private GameDifficulty gameDifficulty;
    @Column(name = "level_name")
    private String levelName;
    private int rows;
    private int columns;
    @Column(name = "water_level")
    private int waterLevel;

    public Level(int levelId, List<Tile> levelInfo, GameDifficulty gameDifficulty, String levelName, int rows, int columns, int waterLevel) {
        this.levelId = levelId;
        this.levelInfo = levelInfo;
        this.gameDifficulty = gameDifficulty;
        this.levelName = levelName;
        this.rows = rows;
        this.columns = columns;
        this.waterLevel = waterLevel;
    }

    public Level() {

    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Tile> getLevel() {
        return levelInfo;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getId() {
        return id;
    }

    public List<Tile> getLevelInfo() {
        return levelInfo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public void setLevel(List<Tile> level) {
        this.levelInfo = level;
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public String getLevelName() {
        return levelName;
    }

}
