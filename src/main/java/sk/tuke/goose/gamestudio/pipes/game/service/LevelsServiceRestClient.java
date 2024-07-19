package sk.tuke.goose.gamestudio.pipes.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.goose.gamestudio.pipes.game.entities.Level;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class LevelsServiceRestClient implements LevelsService{
    @Value("http://localhost:8080/api")
    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void addLevel(Level level) {
        int id = 0;
        for (Level level1: getLevels()) {
            if(level1.getLevelId() > id){
                id = level1.getLevelId();
            }
        }
        level.setLevelId(id+1);
        restTemplate.postForEntity(url + "/level", level, Level.class);
    }

    @Override
    public List<Level> getLevels() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url + "/level", Level[].class).getBody()));
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
