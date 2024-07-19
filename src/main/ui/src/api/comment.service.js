import gsAxios from "./index";

export const getComments = () => gsAxios.get('/comment');

export const postComments = (comment, player, game) => gsAxios.post('/comment',{
    comment, player, game, commentedOn: new Date()
});