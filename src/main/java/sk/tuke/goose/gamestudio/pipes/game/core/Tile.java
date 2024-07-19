package sk.tuke.goose.gamestudio.pipes.game.core;

public class Tile {
    private TileDirection tileDirection;
    private int row;
    private int column;

    public Tile(TileDirection tileDirection, int row, int column) {
        this.tileDirection = tileDirection;
        this.row = row;
        this.column = column;
    }

    public Tile() {

    }

    public TileDirection getDirection() {
        return tileDirection;
    }

    void setDirection(TileDirection tileDirection) {
        this.tileDirection = tileDirection;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}