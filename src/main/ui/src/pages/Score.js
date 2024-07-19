import {useEffect, useState} from "react";
import {getTopScores} from "../api/score.service";
import Scores from "../components/Scores";
import '../App.css';

export default function Score() {
    const [scores, setScores] = useState([]);

    useEffect(() => {
        getTopScores().then(response => {
            setScores(response.data);
        })
    }, []);

    return (
        <header className="score-table">
            <Scores scores={scores}/>
        </header>
    );
}
