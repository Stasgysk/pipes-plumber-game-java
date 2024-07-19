import gsAxios from "./index";

export const getTopScores = () => gsAxios.get('/score');
export const getScores = () => gsAxios.get('/pipes/scores');

export const postScore = (playerId, playerName, game, score, playedOn, levelId) => gsAxios.post('/score',{
    playerId, playerName, game, score, playedOn: new Date(), levelId
});