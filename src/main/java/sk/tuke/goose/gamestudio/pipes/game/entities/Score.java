package sk.tuke.goose.gamestudio.pipes.game.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "player")
public class Score {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "player_id")
    private int playerId;
    @Column(name = "player_name")
    private String playerName;
    @Column(name = "game")
    private String game;
    @Column(name = "score")
    private int score;

    @Column(name = "last_played_date")
    private Date playedOn;
    @Column(name = "last_level_id", nullable = true)
    private Integer levelId;

    public Score(int playerId, String playerName, int score, String game, Integer levelId) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.score = score;
        this.game = game;
        this.playedOn = new Timestamp(new Date().getTime());
        this.levelId = levelId;
    }

    public Score() {

    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", score=" + score +
                ", game='" + game + '\'' +
                ", playedOn=" + playedOn +
                ", levelId=" + levelId +
                '}';
    }
}
