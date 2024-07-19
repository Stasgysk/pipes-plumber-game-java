package sk.tuke.goose.gamestudio.pipes.game.core;

public class Pipe extends Tile {
    private PipeType pipeType;

    public Pipe(PipeType pipeType, TileDirection tileDirection, int row, int column) {
        super(tileDirection, row, column);
        this.pipeType = pipeType;
    }

    public Pipe() {
        super();
    }

    public PipeType getPipeType() {
        return pipeType;
    }

    void setPipeType(PipeType pipeType) {
        this.pipeType = pipeType;
    }

}
