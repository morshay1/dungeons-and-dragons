package Tiles;

public class Empty extends Tile {

    public Empty(Position position) {
        super('.');
        initialize(position);
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }
}
