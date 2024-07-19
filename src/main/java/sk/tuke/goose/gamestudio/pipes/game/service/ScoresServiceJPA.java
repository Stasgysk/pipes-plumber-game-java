package sk.tuke.goose.gamestudio.pipes.game.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import sk.tuke.goose.gamestudio.pipes.game.entities.Score;


import java.sql.Timestamp;
import java.util.List;
@Transactional
public class ScoresServiceJPA implements ScoresService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
    }

    @Override
    public void updateScore(Score score) {
        entityManager.createNativeQuery("update player set score = " + score.getScore() + ", last_level_id= " + score.getLevelId() + ", last_played_date= '" + new Timestamp(System.currentTimeMillis()) +"' where player_name='" + score.getPlayerName() + "'", Score.class)
                .executeUpdate();
    }

    @Override
    public List<Score> getTopScores(String game) {
        TypedQuery<Score> query = entityManager.createQuery("SELECT p FROM player p WHERE p.game = game order by  p.score desc", Score.class).setMaxResults(10);
        return query.getResultList();
    }

    @Override
    public void reset() {

    }
}
