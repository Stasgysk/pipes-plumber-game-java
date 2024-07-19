import './PipesReact.css';

function Field({field, gameMode, onTurnTile}) {

    const handleTileClickContextMenu = (event) => {
        event.preventDefault();
    }

    const rows = [];
    for (let i = 0; i < field.rows; i++) {
        const columns = [];
        for (let j = 0; j < field.columns; j++) {
            columns.push(
                <td key={j} onClick={() => onTurnTile(i, j)} onContextMenu={handleTileClickContextMenu}>
                    {getImage(field.listOfTiles[(i * field.columns) + j], gameMode)}
                </td>);
        }
        if (i < field.waterLevel - 1) {
            columns.push(<td key={field.columns} onContextMenu={handleTileClickContextMenu}></td>);
        } else {
            columns.push(<td key={field.columns} onContextMenu={handleTileClickContextMenu}><img
                src="/images/water.png" alt="water"/></td>);
        }
        rows.push(<tr key={i}>{columns}</tr>);
    }

    return (
        <div className="game-field">
            {field.rows===5 ? (
                <table className="pipes-field-small">
                    <tbody className="game-body">
                    {rows}
                    </tbody>
                </table>
            ) : (
                <table className="pipes-field">
                    <tbody className="game-body">
                    {rows}
                    </tbody>
                </table>
            )}
        </div>
    )
}

function getImage(tile, gameMode) {
    //console.log(tile.solution);
    switch (tile.symbol) {
        case '═':
            if (gameMode && tile.solution) {
                return (<img src="/images/SPipeVM.png" alt="marked"/>)
            } else {
                return (<img src="/images/SPipeV.png" alt="nonMarked"/>)
            }
        case '║':
            if (gameMode && tile.solution) {
                return (<img src="/images/SPipeHM.png" alt="marked"/>)
            } else {
                return (<img src="/images/SPipeH.png" alt="nonMarked"/>)
            }
        case '╚':
            if (gameMode && tile.solution) {
                return (<img src="/images/CPipeNM.png" alt="marked"/>)
            } else {
                return (<img src="/images/CPipeN.png" alt="nonMarked"/>)
            }
        case '╔':
            if (gameMode && tile.solution) {
                return (<img src="/images/CPipeEM.png" alt="marked"/>)
            } else {
                return (<img src="/images/CPipeE.png" alt="nonMarked"/>)
            }
        case '╗':
            if (gameMode && tile.solution) {
                return (<img src="/images/CPipeSM.png" alt="marked"/>)
            } else {
                return (<img src="/images/CPipeS.png" alt="nonMarked"/>)
            }
        case '╝':
            if (gameMode && tile.solution) {
                return (<img src="/images/CPipeWM.png" alt="marked"/>)
            } else {
                return (<img src="/images/CPipeW.png" alt="nonMarked"/>)
            }
        case '╩':
            if (gameMode && tile.solution) {
                return (<img src="/images/TPipeNM.png" alt="marked"/>)
            } else {
                return (<img src="/images/TPipeN.png" alt="nonMarked"/>)
            }
        case '╠':
            if (gameMode && tile.solution) {
                return (<img src="/images/TPipeEM.png" alt="marked"/>)
            } else {
                return (<img src="/images/TPipeE.png" alt="nonMarked"/>)
            }
        case '╦':
            if (gameMode && tile.solution) {
                return (<img src="/images/TPipeSM.png" alt="marked"/>)
            } else {
                return (<img src="/images/TPipeS.png" alt="nonMarked"/>)
            }
        case '╣':
            if (gameMode && tile.solution) {
                return (<img src="/images/TPipeWM.png" alt="marked"/>)
            } else {
                return (<img src="/images/TPipeW.png" alt="nonMarked"/>)
            }
    }
}

export default Field;