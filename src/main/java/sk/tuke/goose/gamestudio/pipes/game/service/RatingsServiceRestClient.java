package sk.tuke.goose.gamestudio.pipes.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.goose.gamestudio.pipes.game.entities.Rating;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class RatingsServiceRestClient implements RatingsService {
    @Value("http://localhost:8080/api")
    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void addRating(Rating rating) {
        restTemplate.postForEntity(url + "/rating", rating, Rating.class);
    }

    @Override
    public int getRating(String game, String player) {
        Rating[] ratingList = Objects.requireNonNull(restTemplate.getForEntity(url + "/rating", Rating[].class).getBody());
        for (Rating rating : ratingList){
            if(Objects.equals(rating.getPlayer(), player)){
                return rating.getRating();
            }
        }
        return 0;
    }

    @Override
    public int getAverageRating(String game) {
        List<Rating> ratingList = Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url + "/rating", Rating[].class).getBody()));
        int averageRating = 0;
        for (Rating rating : ratingList){
            averageRating += rating.getRating();
        }
        averageRating /= ratingList.size();
        return averageRating;
    }

    @Override
    public void setRating(Rating rating) {
        restTemplate.put(url + "/rating", rating, Rating.class);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
