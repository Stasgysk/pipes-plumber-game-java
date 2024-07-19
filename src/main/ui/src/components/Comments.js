import {Table} from "react-bootstrap";

function Comments(props) {
    return(
        <Table striped bordered hover variant="light">
            <thead>
            <tr>
                <th>Player</th>
                <th>Comment</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            {props.comments.map(comment => (
                <tr key = {comment.player}>
                    <td>{comment.player}</td>
                    <td>{comment.comment}</td>
                    <td>{new Date(comment.commentedOn).toLocaleString()}</td>
                </tr>
            ))}
            </tbody>
        </Table>
    )
}

export default Comments;