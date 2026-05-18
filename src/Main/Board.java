package Main;

import java.util.*;

import Players.Player;
import Tiles.Tile;
import Enemies.Enemy;
import Messages.MessageCallback;
import Tiles.Position;
import Tiles.Empty;

public class Board {
    private List<String> board;
    private Player player;
    private List<Tile> tiles;
    private List<Enemy> enemies;
    private MessageCallback messageCallback;
    private int boardHeight;
    private int boardWidth;

    public Board(List<String> board, Player player, MessageCallback messageCallback) {
        this.board = board;
        this.player = player;
        this.tiles = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.messageCallback = messageCallback;
        boardHeight = board.size();
        boardWidth = board.get(0).length();
        createBoard(board);
    }

    public void createBoard(List<String> board) {
        TileFactory tileFactory = new TileFactory();
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                char someChar = board.get(i).charAt(j);
                switch (someChar) {
                    case '#' -> tiles.add(tileFactory.produceWall(new Position(i, j)));
                    case '.' -> tiles.add(tileFactory.produceEmpty(new Position(i, j)));
                    case '@' -> {
                        player.initialize(new Position(i, j), messageCallback);
                        tiles.add(player);
                    }
                    case 's', 'k', 'q', 'z', 'b', 'g', 'w', 'M', 'C', 'K', 'B', 'Q', 'D' -> {
                        Enemy newEnemy = tileFactory.produceEnemy(someChar, new Position(i, j), messageCallback);
                        enemies.add(newEnemy);
                        tiles.add(newEnemy);
                    }
                }
            }
        }
    }

    public void movePlayer(int x, int y) {
        Position currentPlayerPosition = player.getPosition();
        int currentX = currentPlayerPosition.getX();
        int currentY = currentPlayerPosition.getY();
        int boardWidth = board.get(0).length();
        int nextStepIndex = (currentX + x) * boardWidth + (currentY + y);
        Tile nextTile = tiles.get(nextStepIndex);
        player.interact(nextTile);
        reCreateBoard(nextTile);
    }

    public void moveEnemy(Enemy enemy, Position newPosition) {
        if (enemy.getPosition().equals(newPosition)) {
            return;
        }
        int oldIndex = enemy.getPosition().getX() * boardWidth + enemy.getPosition().getY();
        int newIndex = newPosition.getX() * boardWidth + newPosition.getY();

        Tile nextTile = tiles.get(newIndex);

        enemy.interact(nextTile);

        if (enemy.getPosition().equals(newPosition)) {
            tiles.set(oldIndex, new Empty(new Position(oldIndex / boardWidth, oldIndex % boardWidth)));
            tiles.set(newIndex, enemy);
        }
    }

    public void reCreateBoard(Tile tileToSwap) {
        int index1 = tiles.indexOf(tileToSwap);
        int index2 = tileToSwap.getPosition().getY() + (tileToSwap.getPosition().getX() * boardWidth);
        Tile secondTileToSwap = tiles.get(index2);
        tiles.set(index1, secondTileToSwap);
        tiles.set(index2, tileToSwap);
    }

    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    @Override
    public String toString() {
        String output = "";

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                char someChar = tiles.get(j + i * boardWidth).getTile();
                output += someChar;
            }
            output += "\n";
        }
        return output;
    }
}
