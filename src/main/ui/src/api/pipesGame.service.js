import gsAxios from "./index";

const GAME_URL = "/pipes";
const NEW_GAME_URL = GAME_URL + "/newGame";
const TURN_PIPE_URL = GAME_URL + "/turnPipe";
const SET_MODE_URL = GAME_URL + "/setMode";
const CHECK_IF_WIN_URL = GAME_URL + "/checkIfWin";
const FIELD_URL = GAME_URL + "/field";
const LEVEL_URL = GAME_URL + "/levelId";
const getField = () => gsAxios.get(FIELD_URL);
const newGame = (gameDifficulty, levelName) => gsAxios.get(`${NEW_GAME_URL}?gameDifficulty=${gameDifficulty}&levelName=${levelName}`);
const isSolved = () => gsAxios.get(CHECK_IF_WIN_URL);
const turnPipe = (row, column) => gsAxios.get(`${TURN_PIPE_URL}?row=${row+1}&column=${column+1}`);
const setMode = (mode) => gsAxios.post(SET_MODE_URL, {
    mode
});

const getLevelId = () => gsAxios.get(LEVEL_URL);

const fieldService = {getField, newGame, isSolved, turnPipe, setMode, getLevelId}
export default fieldService;

