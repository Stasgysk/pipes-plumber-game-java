package sk.tuke.goose.gamestudio.pipes.game.entities;

import sk.tuke.goose.gamestudio.pipes.game.core.Pipe;
import sk.tuke.goose.gamestudio.pipes.game.core.PipeType;
import sk.tuke.goose.gamestudio.pipes.game.core.TileDirection;


public class Tile {
    private String symbol;
    private TileDirection solutionDirection;

    private Pipe pipe;
    private boolean isSolution;

    public Tile(String symbol, TileDirection direction, TileDirection solutionDirection, PipeType type, int row, int column, boolean isSolution) {
        this.symbol = symbol;
        this.solutionDirection = solutionDirection;
        pipe = new Pipe(type, direction, row, column);
        this.isSolution = isSolution;
    }

    public Tile(){

    }

    public Pipe getPipe() {
        return pipe;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public TileDirection getSolutionDirection() {
        return solutionDirection;
    }

    public void setSolutionDirection(TileDirection solutionDirection) {
        this.solutionDirection = solutionDirection;
    }

    public boolean isSolution() {
        return isSolution;
    }

    public void setSolution(boolean solution) {
        isSolution = solution;
    }

    @Override
    public String toString() {
        return "Level{" +
                "symbol='" + symbol + '\'' +
                ", direction=" + getPipe().getDirection() +
                ", solutionDirection=" + solutionDirection +
                ", type=" + getPipe().getPipeType() +
                ", row=" + getPipe().getRow() +
                ", column=" + getPipe().getColumn() +
                ", isSolution=" + isSolution +
                '}';
    }
}
