package sk.tuke.goose.gamestudio.pipes.game.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    private String comment;
    private String player;
    private String game;
    @Column(name  = "commented_on")
    private Date commentedOn;

    public Comment(String comment, String player, String game) {
        this.comment = comment;
        this.player = player;
        this.game = game;
        this.commentedOn = new Timestamp(new Date().getTime());
    }

    public Comment() {

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

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
