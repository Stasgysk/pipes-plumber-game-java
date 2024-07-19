import PipesReact from "../pipesReact/PipesReact";
import '../App.css';

export default function Game(props) {
    return (
        <PipesReact player={props.player} game={props.game}/>
    );
}