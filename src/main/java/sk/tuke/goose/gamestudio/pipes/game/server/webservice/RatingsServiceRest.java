package sk.tuke.goose.gamestudio.pipes.game.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.goose.gamestudio.pipes.game.entities.Rating;
import sk.tuke.goose.gamestudio.pipes.game.service.MyCustomServices;
import sk.tuke.goose.gamestudio.pipes.game.service.RatingsService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/rating")
public class RatingsServiceRest {
    private final RatingsService ratingsService;
    private final MyCustomServices myCustomServices;

    public RatingsServiceRest(RatingsService ratingsService, MyCustomServices myCustomServices) {
        this.ratingsService = ratingsService;
        this.myCustomServices = myCustomServices;
    }

    @GetMapping
    public List<Rating> getRatings() {
        return myCustomServices.getRatings("pipes");
    }

    @GetMapping("/average")
    public int getAverageRating(){
        return ratingsService.getAverageRating("pipes");
    }

    @PostMapping
    public void addRating(@RequestBody Rating rating) {
        if(rating == null){
            return;
        }
        for (Rating rating1: getRatings()) {
            if(Objects.equals(rating1.getPlayer(), rating.getPlayer())){
                updateRating(rating);
                return;
            }
        }
        ratingsService.addRating(rating);
    }

    @PutMapping
    public void updateRating(@RequestBody Rating rating){
        if(rating == null){
            return;
        }
        ratingsService.setRating(rating);
    }

}
