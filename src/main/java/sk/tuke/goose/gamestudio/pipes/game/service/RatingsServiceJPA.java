package sk.tuke.goose.gamestudio.pipes.game.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import sk.tuke.goose.gamestudio.pipes.game.entities.Rating;

import java.util.List;
@Transactional
public class RatingsServiceJPA implements RatingsService {

    @PersistenceContext
    private EntityManager entityManager;

    public RatingsServiceJPA() {
    }

    @Override
    public void addRating(Rating rating) {
        entityManager.persist(rating);
    }

    @Override
    public int getRating(String game, String player) {
        TypedQuery<Rating> query = entityManager.createQuery("SELECT r FROM rating r WHERE r.game = '" + game + "', r.player = '" + player +"'", Rating.class);
        return query.getResultList().get(0).getRating();
    }

    @Override
    public int getAverageRating(String game) {
        TypedQuery<Rating> query = entityManager.createQuery("SELECT r FROM rating r WHERE r.game = '" + game + "'", Rating.class);
        List<Rating> ratingList = query.getResultList();
        int averageRating = 0;
        for (Rating rating : ratingList){
            averageRating+=rating.getRating();
        }
        averageRating /= ratingList.size();
        return averageRating;
    }

    @Override
    public void setRating(Rating rating) {
        entityManager.createNativeQuery("update rating set rating = " + rating.getRating() + ", game = '" + rating.getGame() + "', rated_on = '" + rating.getRatedOn() +"' where player ='" + rating.getPlayer() + "'", Rating.class)
                .executeUpdate();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM rating").executeUpdate();
    }
}
