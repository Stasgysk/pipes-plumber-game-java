package sk.tuke.goose.gamestudio.pipes.game.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import sk.tuke.goose.gamestudio.pipes.game.entities.Rating;
import sk.tuke.goose.gamestudio.pipes.game.entities.Score;

import java.util.List;

@Transactional
public class MyCustomServicesJPA implements MyCustomServices{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Score> getScores(String game) {
        TypedQuery<Score> query = entityManager.createQuery("SELECT p FROM player p WHERE p.game = game order by  p.score desc", Score.class);
        return query.getResultList();
    }

    @Override
    public List<Rating> getRatings(String game) {
        TypedQuery<Rating> query = entityManager.createQuery("SELECT r FROM rating r WHERE r.game = game order by r.rating desc", Rating.class);
        return query.getResultList();
    }
}
