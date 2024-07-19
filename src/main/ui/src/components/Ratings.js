import {Table} from "react-bootstrap";

function Ratings(props) {
    return(
        <div>
            <h1 style={{marginBottom: '1.5rem'}}>Average: {props.averageRating}</h1>
            <Table striped bordered hover variant="light">
                <thead>
                <tr>
                    <th>Rating</th>
                    <th>Player</th>
                    <th>Game</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                {props.ratings.map(ratings => (
                    <tr key = {ratings.id}>
                        <td>{ratings.rating}</td>
                        <td>{ratings.player}</td>
                        <td>{ratings.game}</td>
                        <td>{new Date(ratings.ratedOn).toLocaleString()}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </div>
    )
}

export default Ratings;
