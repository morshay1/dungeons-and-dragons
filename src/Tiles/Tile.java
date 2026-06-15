package tiles;

public abstract class Tile implements Comparable<Tile> {
    protected char tile;
    protected Position position;

    protected Tile(char tile) {
        this.tile = tile;
    }

    protected void initialize(Position position) {
        this.position = position;
    }

    public char getTile() {
        return this.tile;
    }

    public void setTile(char newTile) {
        this.tile = newTile;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public abstract void accept(Unit unit);

    @Override
    public int compareTo(Tile tile) {
        return this.getPosition().compareTo(tile.getPosition());
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }
}
