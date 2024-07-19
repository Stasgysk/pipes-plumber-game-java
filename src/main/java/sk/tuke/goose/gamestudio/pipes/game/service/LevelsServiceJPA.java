package sk.tuke.goose.gamestudio.pipes.game.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import sk.tuke.goose.gamestudio.pipes.game.entities.Level;

import java.util.List;
@Transactional
public class LevelsServiceJPA implements LevelsService {
    @PersistenceContext
    private EntityManager entityManager;

    public LevelsServiceJPA() {

    }

    @Override
    public void addLevel(Level level) {
        int id = 0;
        for (Level l: getLevels()) {
            if(l.getLevelId()>id){
                id = l.getLevelId();
            }
        }
        id++;
        for (Level l: getLevels()) {
            if(l.getLevelName().equals(level.getLevelName())){
                level.setLevelName(level.getLevelName() + id);
            }
        }
        level.setLevelId(id);
        entityManager.persist(level);
    }

    @Override
    public List<Level> getLevels() {
        TypedQuery<Level> query = entityManager.createQuery("SELECT l FROM Level l", Level.class);
        return query.getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM level").executeUpdate();
    }
}
