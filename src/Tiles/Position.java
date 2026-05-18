package Tiles;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int compareTo(Position position) {
        if (this.x == position.x) {
            if (this.y == position.y) {
                return 0;
            } else {
                return this.y - position.y;
            }
        } else {
            return this.x - position.x;
        }
    }
}