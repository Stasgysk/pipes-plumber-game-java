import {useEffect, useState} from "react";
import {getAverageRating, getRatings} from "../api/rating.service";
import Ratings from "../components/Ratings";

export default function Rating() {
    const [ratings, setRatings] = useState([]);
    const [averageRating, setAverageRatings] = useState([]);

    useEffect(() => {
        getRatings().then(response => {
            setRatings(response.data);
        })
        getAverageRating().then(response => {
            setAverageRatings(response.data);
        })
    }, []);

    return (
        <header className="rating-table">
            <Ratings ratings={ratings} averageRating={averageRating}/>
        </header>
    );
}