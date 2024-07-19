import {Table} from "react-bootstrap";

function Scores(props) {
    return (
        <div>
            <h1 style={{marginBottom: '1.5rem'}}>Top 10 scored players!</h1>
            <Table striped bordered hover variant="light">
                <thead>
                <tr>
                    <th>Player</th>
                    <th>Game</th>
                    <th>Score</th>
                    <th>PlayedOn</th>
                </tr>
                </thead>
                <tbody>
                {props.scores.map(scores => (
                    <tr key={scores.playerId}>
                        <td>{scores.playerName}</td>
                        <td>{scores.game}</td>
                        <td>{scores.score}</td>
                        <td>{new Date(scores.playedOn).toLocaleString()}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </div>
    )
}

export default Scores;