package sk.tuke.goose.gamestudio.pipes.game.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "rating")
public class Rating {

    @Id
    @GeneratedValue
    private int id;

    private int rating;
    private String player;
    private String game;

    @Column(name  = "rated_on")
    private Date ratedOn;

    public Rating(int rating, String player, String game) {
        this.rating = rating;
        this.player = player;
        this.game = game;
        this.ratedOn = new Timestamp(new Date().getTime());
    }

    public Rating() {
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }
}
