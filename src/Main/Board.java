package main;

import java.util.*;

import players.Player;
import tiles.Tile;
import enemies.Enemy;
import messages.CombatCallback;
import messages.MessageCallback;
import tiles.Position;
import tiles.Empty;

public class Board {
    private Player player;
    private List<Tile> tiles;
    private List<Enemy> enemies;
    private MessageCallback messageCallback;
    private CombatCallback combatCallback;
    private int boardHeight;
    private int boardWidth;

    public Board(List<String> board, Player player, MessageCallback messageCallback, CombatCallback combatCallback) {
        this.player = player;
        this.tiles = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.messageCallback = messageCallback;
        this.combatCallback = combatCallback;
        this.boardHeight = board.size();
        this.boardWidth = board.get(0).length();
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
                        player.initialize(new Position(i, j), messageCallback, combatCallback);
                        tiles.add(player);
                    }
                    case 's', 'k', 'q', 'z', 'b', 'g', 'w', 'M', 'C', 'K', 'B', 'Q', 'D' -> {
                        Enemy newEnemy = tileFactory.produceEnemy(someChar, new Position(i, j), messageCallback, combatCallback);
                        enemies.add(newEnemy);
                        tiles.add(newEnemy);
                    }
                }
            }
        }
    }

    public void movePlayer(int x, int y) {
        Position currentPlayerPosition = player.getPosition();

        int oldIndex = currentPlayerPosition.getX() * boardWidth + currentPlayerPosition.getY();

        int newX = currentPlayerPosition.getX() + x;
        int newY = currentPlayerPosition.getY() + y;

        int newIndex = newX * boardWidth + newY;

        Tile nextTile = tiles.get(newIndex);

        player.interact(nextTile);

        if (player.getPosition().getX() == newX && player.getPosition().getY() == newY) {
            tiles.set(oldIndex, new Empty(currentPlayerPosition));
            tiles.set(newIndex, player);
        }

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

    public Player getPlayer() {
        return player;
    }

    public void removeDeadEnemies() {
        enemies.removeIf(Enemy::isDead);
    }

    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public List<Tile> getTiles() {
        return tiles;
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
