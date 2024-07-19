package sk.tuke.goose.gamestudio.pipes.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import sk.tuke.goose.gamestudio.pipes.game.entities.Tile;
import sk.tuke.goose.gamestudio.pipes.game.entities.Level;
import sk.tuke.goose.gamestudio.pipes.game.handler.GameDifficulty;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LevelsServiceJDBC implements LevelsService {
    private static final String INSERT_STATEMENT_LEVEL = "INSERT INTO level (level_info, level_type, level_name, rows, columns, water_level) VALUES (to_json(?::json), ?::level_type, ?, ?, ?, ?)";
    private static final String SELECT_STATEMENT_LEVEL = "SELECT level_id, level_info, level_type, level_name, rows, columns, water_level FROM level ORDER BY level_id ASC";

    private final DataSource dataSource;

    public LevelsServiceJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addLevel(Level level) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT_LEVEL)
        ) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = "";
            try {
                json = objectMapper.writeValueAsString(level.getLevel());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            statement.setObject(1, json);
            statement.setString(2, level.getGameDifficulty().toString());
            statement.setString(3, level.getLevelName());
            statement.setInt(4, level.getRows());
            statement.setInt(5, level.getColumns());
            statement.setInt(6, level.getWaterLevel());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new LevelException("Problem adding level", e);
        }
    }

    @Override
    public List<Level> getLevels() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT_LEVEL)
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Level> levelList = new ArrayList<>();
                ObjectMapper objectMapper = new ObjectMapper();
                while (resultSet.next()){
                    try {
                        List<Tile> tile = objectMapper.readValue(resultSet.getString(2), new TypeReference<>() {
                        });
                        Level level = new Level(resultSet.getInt(1), tile, GameDifficulty.valueOf(resultSet.getString(3)), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7));
                        levelList.add(level);

                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                return levelList;
            }
        } catch (SQLException e) {
            throw new LevelException("Problem getting levels", e);
        }
    }

    public void reset() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("DELETE FROM level");
        } catch (SQLException e) {
            throw new LevelException("Problem deleting level", e);
        }
    }

//    @Override
//    public LevelInfo getLevelById(int id) {
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
//             PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT_LEVEL)
//        ) {
//            try (ResultSet resultSet = statement.executeQuery()) {
//                List<Level> level = new ArrayList<>();
//                ObjectMapper objectMapper = new ObjectMapper();
//                while (resultSet.next()){
//                    if(resultSet.getInt(1) == id){
//                        try {
//                            level = objectMapper.readValue(resultSet.getString(2), new TypeReference<>() {});
//                        } catch (IOException e){
//                            e.printStackTrace();
//                        }
//                        return new LevelInfo(resultSet.getInt(1), level, GameDifficulty.valueOf(resultSet.getString(3)), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7));
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }

}
