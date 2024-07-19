import gsAxios from "./index";

export const getRatings = () => gsAxios.get('/rating');

export const postRating = (rating, player, game) => gsAxios.post('/rating',{
    rating, player, game, ratedOn: new Date()
});

export const getAverageRating = () => gsAxios.get('/rating/average');