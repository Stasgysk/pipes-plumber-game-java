package sk.tuke.goose.gamestudio.pipes.game.service;

import sk.tuke.goose.gamestudio.pipes.game.entities.Comment;

import java.util.List;

public interface CommentsService {
    void addComment(Comment comment);

    List<Comment> getComments(String game);

    void updateComment(Comment comment);
    void reset();
}
