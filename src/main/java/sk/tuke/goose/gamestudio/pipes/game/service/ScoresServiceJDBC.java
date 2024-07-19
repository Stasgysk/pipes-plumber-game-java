package sk.tuke.goose.gamestudio.pipes.game.service;

import org.springframework.stereotype.Service;
import sk.tuke.goose.gamestudio.pipes.game.entities.Score;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoresServiceJDBC implements ScoresService {
    private static final String INSERT_STATEMENT_PLAYER = "INSERT INTO player (player_name, score) VALUES (?, ?)";
    private static final String SELECT_STATEMENT_PLAYER = "SELECT player_id, player_name, score, last_level_id FROM player ORDER BY player_id ASC";
    private final DataSource dataSource;

    public ScoresServiceJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addScore(Score score) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT_PLAYER)
        ) {
            statement.setString(1, score.getPlayerName());
            statement.setInt(2, score.getScore());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException("Problem adding score", e);
        }
    }

    @Override
    public void updateScore(Score score) {
        String updateStatement = "update player set score = " + score.getScore() + ", last_level_id= " + score.getLevelId() + ", last_played_date= '" + new Timestamp(System.currentTimeMillis()) +"' where player_name='" + score.getPlayerName() + "'";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateStatement)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException("Problem updating score", e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT_PLAYER)
        ) {
            try (ResultSet rs = statement.executeQuery()) {
                List<Score> scoreList = new ArrayList<>();
                while (rs.next()){
                    Score score = new Score(rs.getInt(1) ,rs.getString(2), rs.getInt(3), rs.getString(4),rs.getInt(5));
                    scoreList.add(score);

                }
                return scoreList;
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem getting score", e);
        }

    }

    @Override
    public void reset() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("DELETE FROM player");
        } catch (SQLException e) {
            throw new ScoreException("Problem deleting score", e);
        }
    }

}
