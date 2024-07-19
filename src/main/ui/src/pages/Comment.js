import {useEffect, useState} from "react";
import {getComments} from "../api/comment.service";
import Comments from "../components/Comments";

export default function Comment() {
    const [comment, setComment] = useState([]);

    useEffect(() => {
        getComments().then(response => {
            setComment(response.data);
        })
    }, []);

    return (
        <header className="rating-table">
            <Comments comments={comment}/>
        </header>
    );
}