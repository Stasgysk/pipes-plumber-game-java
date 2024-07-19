package sk.tuke.goose.gamestudio.pipes.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.goose.gamestudio.pipes.game.entities.Score;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ScoresServiceRestClient implements ScoresService {
    @Value("${remote.server.api}")
    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url + "/score", score, Score.class);
    }

    @Override
    public void updateScore(Score score) {
        restTemplate.put(url + "/score", score, Score.class);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url + "/score", Score[].class).getBody()));
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
