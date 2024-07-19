package sk.tuke.goose.gamestudio.pipes.game.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.goose.gamestudio.pipes.game.entities.Comment;
import sk.tuke.goose.gamestudio.pipes.game.service.CommentsService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentsServiceRest {
    private final CommentsService commentsService;

    public CommentsServiceRest(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping
    public List<Comment> getComments() {
        return commentsService.getComments("pipes");
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        if(comment == null){
            return;
        }
        for (Comment comment1: getComments()) {
            if(comment1.getPlayer().equals(comment.getPlayer())){
                updateRating(comment);
                return;
            }
        }
        commentsService.addComment(comment);
    }

    @PutMapping
    public void updateRating(@RequestBody Comment comment){
        if(comment == null){
            return;
        }
        commentsService.updateComment(comment);
    }

}
