package Main;

import java.util.*;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import Players.Player;
import Enemies.Enemy;
import Tiles.Position;

public class GameController {
    private CLI cli;
    private String levelsPath;
    private int currentLevel;
    private int levelsAmount;
    private Player player;
    private Board board;

    public GameController(String levelsPath) {
        this.cli = new CLI();
        this.levelsPath = levelsPath;
        this.currentLevel = 1;
        this.levelsAmount = countLevels(levelsPath);
    }

    public void start() {
        if (levelsAmount == 0) {
            cli.display("No level files found. Exiting game.");
            return;
        }
        player = choosePlayer();
        CLI cli = new CLI();
        while (!player.isDead() && currentLevel <= levelsAmount) {
            try {
                List<String> stringBoard = loadBoard(levelsPath, currentLevel);
                board = new Board(stringBoard, player, cli.message);
                new GameWindow(board, this);
            } catch (IOException e) {
                cli.display("Failed to process level " + currentLevel + ".");
                return;
            }
            while (!board.getEnemies().isEmpty() && !player.isDead()) {
                cli.display(board.toString());
                cli.display(player.describe());
                gameTick();
            }
            if (player.isDead()) {
                cli.display(board.toString());
                cli.display("You lost.");
            } else {
                currentLevel++;
            }
        }
        if (!player.isDead()) {
            cli.display("You win!");
        }
    }

    public Player choosePlayer() {
        TileFactory tileFactory = new TileFactory();
        List<Player> players = tileFactory.listPlayers();
        cli.display("Select player: ");
        while (true) {
            int i = 1;
            for (Player player : players) {
                cli.display(i + ". " + player.describe());
                i++;
            }
            try {
                int userChoice = Integer.parseInt(cli.readInput());
                if (userChoice >= 1 && userChoice <= players.size()) {
                    Player chosenPlayer = players.get(userChoice - 1);
                    cli.display("You have selected: \n" + chosenPlayer.getName());
                    return chosenPlayer;
                }
                cli.display("Invalid player choice.");
            } catch (NumberFormatException e) {
                cli.display("Please enter a number.");
            }
        }
    }

    public List<String> loadBoard(String levelsPath, int level) throws IOException {
        levelsPath = levelsPath + "\\level" + level + ".txt";
        Path path = Path.of(levelsPath);
        List<String> stringBoard = Files.readAllLines(path);
        return stringBoard;
    }

    public int countLevels(String levelsPath) {
        try (Stream<Path> files = Files.list(Paths.get(levelsPath))) {
            return (int) files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".txt"))
                    .count();
        } catch (IOException e) {
            return 0;
        }
    }

    public void gameTick() {
        playerAction();
        player.onGameTick();

        for (Enemy enemy : board.getEnemies()) {
            if (!enemy.isDead()) {
                Position newPosition = enemy.onGameTick(player);
                board.moveEnemy(enemy, newPosition);
            }
        }
        board.removeDeadEnemies();
    }

    public void playerAction() {
        while (true) {
            String action = cli.readInput();
            switch (action) {
                case "w" -> {
                    board.movePlayer(-1, 0);
                    return;
                }
                case "s" -> {
                    board.movePlayer(1, 0);
                    return;
                }
                case "a" -> {
                    board.movePlayer(0, -1);
                    return;
                }
                case "d" -> {
                    board.movePlayer(0, 1);
                    return;
                }
                case "e" -> {
                    player.onAbilityCast(board.getEnemies());
                    return;
                }
                default -> {
                    cli.display("Invalid input. Try again.");
                }
            }
        }
    }

    public void guiPlayerAction(String action) {
        switch (action) {
            case "w" -> board.movePlayer(-1, 0);

            case "s" -> board.movePlayer(1, 0);

            case "a" -> board.movePlayer(0, -1);

            case "d" -> board.movePlayer(0, 1);

            case "e" -> player.onAbilityCast(board.getEnemies());

            default -> {
                return;
            }
        }

        player.onGameTick();

        for (Enemy enemy : board.getEnemies()) {
            if (!enemy.isDead()) {
                Position newPosition = enemy.onGameTick(player);
                board.moveEnemy(enemy, newPosition);
            }
        }

        board.removeDeadEnemies();
    }
}
