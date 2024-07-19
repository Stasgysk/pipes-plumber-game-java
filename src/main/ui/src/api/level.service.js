import gsAxios from "./index";

export const getLevels = () => gsAxios.get('/level');

// export const postLevel = (comment, player, game) => gsAxios.post('/level',{
//     comment, player, game, commentedOn: new Date()
// });