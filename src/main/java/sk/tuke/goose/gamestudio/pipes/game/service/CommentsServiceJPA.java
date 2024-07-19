package sk.tuke.goose.gamestudio.pipes.game.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import sk.tuke.goose.gamestudio.pipes.game.entities.Comment;

import java.util.List;

@Transactional
public class CommentsServiceJPA implements CommentsService {
    @PersistenceContext
    private EntityManager entityManager;

    public CommentsServiceJPA() {
    }

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM comment c WHERE c.game = game", Comment.class);
        return query.getResultList();
    }

    @Override
    public void updateComment(Comment comment) {
        entityManager.createNativeQuery("update comment set comment = '" + comment.getComment() + "', game = '" + comment.getGame() + "', commented_on = '" + comment.getCommentedOn() +"' where player='" + comment.getPlayer() + "'", Comment.class)
                .executeUpdate();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM comment").executeUpdate();
    }
}
