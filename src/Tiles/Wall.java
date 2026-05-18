package Tiles;

public class Wall extends Tile {

    public Wall(Position position) {
        super('#');
        initialize(position);
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

}
