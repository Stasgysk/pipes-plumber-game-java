package sk.tuke.goose.gamestudio.pipes.game.core;

import sk.tuke.goose.gamestudio.pipes.game.colours.ConsoleColours;
import sk.tuke.goose.gamestudio.pipes.game.entities.Tile;

import java.util.*;

import static sk.tuke.goose.gamestudio.pipes.game.core.FieldState.PLAYING;
import static sk.tuke.goose.gamestudio.pipes.game.core.FieldState.SOLVED;
import static sk.tuke.goose.gamestudio.pipes.game.core.PipeType.*;
import static sk.tuke.goose.gamestudio.pipes.game.core.TileDirection.*;

public class Field {
    private FieldState fieldState = PLAYING;
    private final int rows;
    private final int columns;
    private int waterLevel;
    private final List<Tile> listOfTiles;

    public Field(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        Random rand = new Random();
        waterLevel = rand.nextInt((rows / 2) + 1) + 1 + rows / 2; // water level 0-rows
        if(waterLevel > rows) {
            waterLevel = rows;
        }
        listOfTiles = new ArrayList<>();
        generateGameField();
    }

    public Field(int rows, int columns, List<Tile> listOfTiles, int waterLevel) {
        this.columns = columns;
        this.rows = rows;
        this.listOfTiles = listOfTiles;
        this.waterLevel = waterLevel;
    }

    public List<Tile> getListOfTiles() {
        return listOfTiles;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

    public void setFieldState(FieldState fieldState) {
        this.fieldState = fieldState;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    private void generateGameField() {
        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                setRandomTile(row, column);
            }
        }
        int count = 0;
        int startPos = (waterLevel * columns) - 1;
        listOfTiles.get(startPos).setSymbol("*");
        listOfTiles.get(startPos).setSolution(true);
        int[] prevDirection = new int[rows * columns];
        int row = waterLevel - 1;
        int column = columns - 1;
        generateSolution(row, column, prevDirection, count);
        setSolutionTileToRightSymbol();
    }

    private void generateSolution(int row, int column, int[] prevDirection, int count) {
        while (column > 0) {
            Random rand = new Random();
            int direction = rand.nextInt(3);
            if (checkForBadGenerationSituations(count, prevDirection, direction)) {
                continue;
            }
            if (direction == 0) {
                row--;
                if (row < 0) {
                    row++;
                    continue;
                }
            } else if (direction == 1) {
                column--;
            } else {
                row++;
                if (row > rows - 1) {
                    row--;
                    continue;
                }
            }
            int pos = (column + (row * columns));

            if (pos >= listOfTiles.size()) {
                continue;
            }
            prevDirection[count] = direction;

            listOfTiles.get(pos).setSymbol("*");
            listOfTiles.get(pos).setSolution(true);
            count++;
        }
    }

    private void setSolutionTileToRightSymbol() {
        for (int pos = 0; pos < listOfTiles.size(); pos++) {
            Tile tile = listOfTiles.get(pos);
            if (tile.isSolution()) {
                int posOFNorthSymbol = pos - columns;
                int posOFEastSymbol = pos + 1;
                if ((pos + 1) % columns == 0) {
                    posOFEastSymbol = -1;
                }
                int posOFSouthSymbol = pos + columns;
                int posOFWestSymbol = pos - 1;
                if (pos % columns == 0) {
                    posOFWestSymbol = -1;
                }
                boolean isNorthSymbolIsSolution = isPosIsSolution(posOFNorthSymbol);
                boolean isEastSymbolIsSolution = isPosIsSolution(posOFEastSymbol);
                boolean isSouthSymbolIsSolution = isPosIsSolution(posOFSouthSymbol);
                boolean isWestSymbolIsSolution = isPosIsSolution(posOFWestSymbol);

                setSolutionSymbol(isNorthSymbolIsSolution, isEastSymbolIsSolution, isSouthSymbolIsSolution, isWestSymbolIsSolution, pos);
            }
        }
    }

    private void setSolutionSymbol(boolean isNorthSymbolIsSolution, boolean isEastSymbolIsSolution, boolean isSouthSymbolIsSolution, boolean isWestSymbolIsSolution, int pos) {
        if (isNorthSymbolIsSolution) {
            ifNorthIsSolution(pos, isEastSymbolIsSolution, isSouthSymbolIsSolution, isWestSymbolIsSolution);
        } else if (isEastSymbolIsSolution) {
            ifEastIsSolution(pos, isSouthSymbolIsSolution, isWestSymbolIsSolution);
        } else if (isSouthSymbolIsSolution) {
            if (isWestSymbolIsSolution) {
                setListOfTilesPosInfo(pos, "╗", SOUTH, CORNER);
                setRandomDirection(pos);
            } else {
                setListOfTilesPosInfo(pos, "╔", EAST, CORNER);
                setRandomDirection(pos);
            }
        } else {
            setListOfTilesPosInfo(pos, "=", EAST, STRAIGHT);
            setRandomDirection(pos);
        }
    }

    private void ifNorthIsSolution(int pos, boolean isEastSymbolIsSolution, boolean isSouthSymbolIsSolution, boolean isWestSymbolIsSolution) {
        if (isEastSymbolIsSolution) {
            setListOfTilesPosInfo(pos, "╚", NORTH, CORNER);
            setRandomDirection(pos);
        } else if (isSouthSymbolIsSolution) {
            setListOfTilesPosInfo(pos, "║", NORTH, STRAIGHT);
            setRandomDirection(pos);
        } else if (isWestSymbolIsSolution) {
            setListOfTilesPosInfo(pos, "╝", WEST, CORNER);
            setRandomDirection(pos);
        } else {
            setListOfTilesPosInfo(pos, "╚", NORTH, CORNER);
            setRandomDirection(pos);
        }
    }

    private void ifEastIsSolution(int pos, boolean isSouthSymbolIsSolution, boolean isWestSymbolIsSolution) {
        if (isSouthSymbolIsSolution) {
            setListOfTilesPosInfo(pos, "╔", EAST, CORNER);
            setRandomDirection(pos);
        } else if (isWestSymbolIsSolution) {
            setListOfTilesPosInfo(pos, "=", EAST, STRAIGHT);
            setRandomDirection(pos);
        } else {
            setListOfTilesPosInfo(pos, "=", EAST, STRAIGHT);
            setRandomDirection(pos);
        }
    }

    private void setListOfTilesPosInfo(int pos, String symbol, TileDirection tileDirection, PipeType pipeType) {
        listOfTiles.get(pos).setSymbol(symbol);
        listOfTiles.get(pos).getPipe().setDirection(tileDirection);
        listOfTiles.get(pos).setSolutionDirection(tileDirection);
        listOfTiles.get(pos).getPipe().setPipeType(pipeType);
    }

    private void setRandomDirection(int pos) {
        TileDirection tileDirection = TileDirection.generateRandomDirection();
        if (listOfTiles.get(pos).getPipe().getPipeType().equals(STRAIGHT)) {
            String symbol = getPipeSymbol(STRAIGHT, tileDirection);
            setPosSymbolAndDirection(pos, symbol, tileDirection);
        } else if (listOfTiles.get(pos).getPipe().getPipeType().equals(CORNER)) {
            String symbol = getPipeSymbol(CORNER, tileDirection);
            setPosSymbolAndDirection(pos, symbol, tileDirection);
        } else {
            String symbol = getPipeSymbol(TPIPE, tileDirection);
            setPosSymbolAndDirection(pos, symbol, tileDirection);
        }
    }

    private void setPosSymbolAndDirection(int pos, String symbol, TileDirection tileDirection) {
        listOfTiles.get(pos).setSymbol(symbol);
        listOfTiles.get(pos).getPipe().setDirection(tileDirection);
    }

    private boolean isPosIsSolution(int pos) {
        if (pos >= 0 && pos < listOfTiles.size()) {
            return listOfTiles.get(pos).isSolution();
        }
        return false;
    }

    private boolean checkForBadGenerationSituations(int count, int[] prevDirection, int direction) {
        if (count > 0) {
            if (prevDirection[count - 1] == 0 && direction == 2) {
                return true;
            }
            if (prevDirection[count - 1] == 2 && direction == 0) {
                return true;
            }
            if (count > 1) {
                if (prevDirection[count - 2] == 0 && prevDirection[count - 1] == 1 && direction == 2) {
                    return true;
                }
                if (prevDirection[count - 2] == 2 && prevDirection[count - 1] == 1 && direction == 0) {
                    return true;
                }
                return prevDirection[count - 2] == 1 && prevDirection[count - 1] == 1 && direction == 1;
            }

        }
        return false;
    }

    private void setRandomTile(int row, int column) {
        PipeType pipeType = PipeType.generateRandomType();
        TileDirection tileDirection = TileDirection.generateRandomDirection();
        Tile tile = new Tile(getPipeSymbol(pipeType, tileDirection), tileDirection, null, pipeType, row, column, false);
        listOfTiles.add(tile);
    }

    public boolean isSolved() {
        for (Tile tile : listOfTiles) {
            if (!isTileSolved(tile)) {
                return false;
            }
        }
        return true;
    }

    private boolean isTileSolved(Tile tile) {
        if (tile.isSolution()) {
            if (tile.getPipe().getPipeType() == STRAIGHT) {
                if (tile.getSolutionDirection() == NORTH || tile.getSolutionDirection() == SOUTH) {
                    return tile.getPipe().getDirection() == SOUTH || tile.getPipe().getDirection() == NORTH;
                } else {
                    return tile.getPipe().getDirection() == EAST || tile.getPipe().getDirection() == WEST;
                }
            } else {
                return tile.getSolutionDirection() == tile.getPipe().getDirection();
            }
        }
        return true;
    }

    private String getPipeSymbol(PipeType pipeType, TileDirection tileDirection) {
        if (pipeType == PipeType.STRAIGHT) {
            if ((tileDirection == SOUTH) || (tileDirection == NORTH)) {
                return "║";
            }
            return "═";
        } else if (pipeType == CORNER) {
            if (tileDirection == NORTH) {
                return "╚";
            } else if (tileDirection == EAST) {
                return "╔";
            } else if (tileDirection == SOUTH) {
                return "╗";
            } else {
                return "╝";
            }
        } else {
            if (tileDirection == NORTH) {
                return "╩";
            } else if (tileDirection == EAST) {
                return "╠";
            } else if (tileDirection == SOUTH) {
                return "╦";
            } else {
                return "╣";
            }
        }
    }

    public void rotatePipe(int pos, boolean ifEasyMode) {
        String symbol;
        TileDirection tileDirection = listOfTiles.get(pos).getPipe().getDirection();
        PipeType pipeType = listOfTiles.get(pos).getPipe().getPipeType();
        TileDirection nextTileDirection = tileDirection.getNextDirection(tileDirection);
        symbol = getPipeSymbol(pipeType, nextTileDirection);
        listOfTiles.get(pos).getPipe().setDirection(nextTileDirection);
        if (ifEasyMode) {
            setColourDependingOnSolution(pos, symbol);
        } else {
            listOfTiles.get(pos).setSymbol(symbol);
        }
        if (isSolved()) {
            setFieldState(SOLVED);
        }
    }

    private void setColourDependingOnSolution(int pos, String symbol) {
        if (listOfTiles.get(pos).isSolution()) {
            if (isTileSolved(listOfTiles.get(pos))) {
                listOfTiles.get(pos).setSymbol(ConsoleColours.BLUE_TEXT + symbol + ConsoleColours.RESET_TEXT);
            } else {
                listOfTiles.get(pos).setSymbol(ConsoleColours.YELLOW_TEXT + symbol + ConsoleColours.RESET_TEXT);
            }
        } else {
            listOfTiles.get(pos).setSymbol(ConsoleColours.RED_TEXT + symbol + ConsoleColours.RESET_TEXT);
        }
    }

}
