import {useForm} from "react-hook-form";
import {Button, Form} from "react-bootstrap";
import {postRating} from "../api/rating.service";
import {postComments} from "../api/comment.service";
import "./Popup.css"
import {getScores, postScore} from "../api/score.service";
import {useEffect, useState} from "react";
import fieldService from "../api/pipesGame.service";

export default function RatingAndCommentForm(props) {
    const {register, handleSubmit, formState: {errors}} = useForm({mode: "onChange"});
    const [scores, setScores] = useState([]);
    const [levelId, setLevelId] = useState([]);

    const fetchData = () => {
        getScores().then(response => {
            setScores(response.data);
        })
        fieldService.getLevelId().then(respone => {
            setLevelId(respone.data);
        })
    }

    useEffect(() => {
        fetchData()
    }, []);
    const onSubmit = data => {
        fetchData();
        postRating(data.rating, props.player, props.game).then(() => {
            postComments(data.comment, props.player, props.game).then(() =>{
                const box = document.getElementById("game-over-popup");
                box.style.display = 'none';
                const currScore = scores.find(element => element.playerName === props.player);
                let score = 0;
                if(currScore){
                    if(props.fieldState === 'SOLVED'){
                        score = currScore.score + 1;
                    } else {
                        score = currScore.score;
                    }
                    const currScoreId = currScore.playerId;
                    postScore(currScoreId, props.player, props.game, score, new Date(), levelId);
                } else {
                    postScore(1, props.player, props.game, 1, new Date(), levelId);
                }
                props.fetchData();
            });
        });
    };

    return (
        <div className="rating-comment-form">
            <Form onSubmit={handleSubmit(onSubmit)}>
                <Form.Group className="mb-3" controlId="ratingForm">
                    <div className="form">
                        <Form.Label>Rating</Form.Label>
                        <input className="form-control"
                               type="number"
                               {...register("rating", {
                                   min: {value: 0, message: "Rating is too small!"},
                                   max: {value: 10, message: "Rating is too big! (10 max)"},
                                   required: {value: true, message: "Rating required!"}
                               })}
                               placeholder="Enter your rating here."/>
                        <Form.Text className="text-muted">
                            {errors.rating?.message}
                        </Form.Text>
                    </div>
                    <div className="form">
                        <Form.Label>Comment</Form.Label>
                        <input className="form-control"
                               type="text"
                               {...register("comment", {
                                   minLength: {value: 3, message: "Comment is too short!"},
                                   maxLength: {value: 100, message: "Comment is too long!"},
                                   required: {value: true, message: "Comment is required!"}
                               })}
                               placeholder="Enter your comment here."/>
                        <Form.Text className="text-muted">
                            {errors.comment?.message}
                        </Form.Text>
                    </div>
                </Form.Group>
                <div className="rating-comment-button">
                <Button variant="primary" type="submit">
                    Send
                </Button>
                </div>
            </Form>
        </div>
    );
}