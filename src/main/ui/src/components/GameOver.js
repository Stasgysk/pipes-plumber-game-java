import './Popup.css';
import RatingAndCommentForm from "./RatingAndCommentForm";

export default function GameOver(props) {
    return (props.trigger) ? (
        <div className="game-over-popup" id={"game-over-popup"}>
            <div className="popup-inner">
                <div className="game-over-text">
                    {props.fieldState === 'FAILED' ? (
                        <h1>Game Over! You lost!</h1>
                    ) : (
                        <h1>You won!</h1>
                    )}
                </div>
                <RatingAndCommentForm player={props.player} game={props.game} fetchData={props.fetchData} fieldState={props.fieldState}/>
            </div>
        </div>
    ) : "";
}