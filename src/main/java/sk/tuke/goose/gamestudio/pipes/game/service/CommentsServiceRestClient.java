package sk.tuke.goose.gamestudio.pipes.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.goose.gamestudio.pipes.game.entities.Comment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CommentsServiceRestClient implements CommentsService {
    @Value("http://localhost:8080/api")
    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void addComment(Comment comment) {
        restTemplate.postForEntity(url + "/comment", comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url + "/comment", Comment[].class).getBody()));
    }

    @Override
    public void updateComment(Comment comment) {
        restTemplate.put(url + "/comment", comment, Comment.class);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
