import {Button} from "react-bootstrap";
import {useEffect, useState} from "react";
import fieldService from "../api/pipesGame.service";
import Field from "./Field";
import './PipesReact.css';
import GameOver from "../components/GameOver";
import {getScores} from "../api/score.service";
import LevelForm from "../components/LevelForm";

function PipesReact(props) {
    const [gameOptions, setGameOptions] = useState({
        gameMode: false,
        levelName: "default",
        gameDifficulty: "EASY",
    });
    const [isAllSet, setIsAllSet] = useState(false);
    const [field, setField] = useState(null);
    const [scores, setScores] = useState([]);
    const [timeLeft, setTimeLeft] = useState(120);

    useEffect(() => {
        fetchData();
        const interval = setInterval(() => {
            setTimeLeft((prevTimeLeft) => prevTimeLeft - 1);
        }, 1000);
        if(timeLeft <= 0){
            field.fieldState = 'FAILED'
            clearInterval(interval)
        }
        return () => clearInterval(interval);
    }, [timeLeft]);

    const fetchData = () => {
        getScores().then(response => {
            setScores(response.data);
        })
    }



    const handleTurnTile = (row, column) => {
        if (field.fieldState !== 'SOLVED' || field.fieldState !== 'FAILED' ) {
            if(row < 0 || column < 0 || row > field.rows || column > field.columns){
                return;
            }
            fieldService.turnPipe(row, column).then(response => {
                setField(response.data);
            })
        }
    }

    const createNewLevel = (levelNameN, gameDifficultyN) => {
        fieldService.newGame(gameDifficultyN, levelNameN).then(response => {
            setField(response.data);
            console.log(response.data);
        })
        switch (gameDifficultyN) {
            case 'EASY' :
                setTimeLeft(60);
                break
            case 'MEDIUM' :
                setTimeLeft(60);
                break
            case 'HARD' :
                setTimeLeft(60);
                break
        }
    }

    const fetchLevelData = (gameModeN, levelNameN, gameDifficultyN, isAllSetN) => {
        setGameOptions({
            gameMode: gameModeN,
            levelName: levelNameN,
            gameDifficulty: gameDifficultyN
        });
        setIsAllSet(isAllSetN);
        createNewLevel(levelNameN, gameDifficultyN);
    }

    const changeState = () => {
        setIsAllSet(false);
        field.fieldState = 'PLAYING';
    }

    return (
        <div className="game-container">
            <h1>Plumber Game</h1>
            <div className="pipes-toolbar">
                <h3>State: {field?.fieldState} </h3>
                <h3>Time left: {timeLeft} </h3>
                <h3>Score: {scores ? scores?.find(element => element.playerName === props.player)?.score : 0}</h3>
                <Button variant="primary" type="submit" onClick={changeState}>
                    New Game
                </Button>
            </div>
            <div>
                {!isAllSet && <LevelForm fetchLevelData={fetchLevelData}/>}
                {field &&
                    <Field field={field}
                           gameMode={gameOptions.gameMode}
                           onTurnTile={handleTurnTile}/>}
                {field?.fieldState === 'SOLVED' &&
                    <GameOver trigger={true} player={props.player} game={props.game} fetchData={fetchData} fieldState={field?.fieldState}/>}
                {field?.fieldState === 'FAILED' &&
                    <GameOver trigger={true} player={props.player} game={props.game} fetchData={fetchData} fieldState={field?.fieldState}/>}
                {field?.fieldState !== 'PLAYING' &&
                    document.getElementById("game-over-popup")?.style.display === 'none'
                    && (
                        <div className="game-over-popup-button" id={"game-over-popup"}>
                            <div className="game-over-text">
                                <Button className="game-over-button" variant="primary" type="submit"
                                        onClick={changeState}>
                                    New Game
                                </Button>
                            </div>
                        </div>
                    )}
            </div>
        </div>
    )
}

export default PipesReact;