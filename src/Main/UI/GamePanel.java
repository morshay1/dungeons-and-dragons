package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.Board;
import main.GameController;
import tiles.Tile;
import tiles.Position;
import players.Warrior;
import players.Mage;
import players.Rogue;
import players.Hunter;
import java.util.List;

public class GamePanel extends JPanel {
    private static final int TILE_SIZE = 80;
    private static final int VIEW_WIDTH = 9;
    private static final int VIEW_HEIGHT = 7;

    private GameController controller;
    private Board board;

    private BufferedImage ground;
    private Map<Character, BufferedImage> images;
    private GameWindow window;
    private List<Position> fightPositions = new ArrayList<>();

    public GamePanel(GameController controller, Board board, GameWindow window) {
        this.controller = controller;
        this.board = board;
        this.window = window;

        int visibleCols = Math.min(VIEW_WIDTH, board.getBoardWidth());
        int visibleRows = Math.min(VIEW_HEIGHT, board.getBoardHeight());

        setPreferredSize(new Dimension(
                visibleCols * TILE_SIZE,
                visibleRows * TILE_SIZE));

        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());

        setBorder(BorderFactory.createLineBorder(new Color(70, 45, 20), 6));

        setFocusable(true);
        requestFocusInWindow();
        loadImages();
        setupKeyBindings();
    }

    private void loadImages() {
        try {
            ground = ImageIO.read(new File("assets/illustrations/ground.png"));

            images = new HashMap<>();

            images.put('#', ImageIO.read(new File("assets/illustrations/bush.png")));

            images.put('1', ImageIO.read(new File("assets/illustrations/warrior.png")));
            images.put('2', ImageIO.read(new File("assets/illustrations/mage.png")));
            images.put('3', ImageIO.read(new File("assets/illustrations/rogue.png")));
            images.put('4', ImageIO.read(new File("assets/illustrations/hunter.png")));
            images.put('5', ImageIO.read(new File("assets/illustrations/warrior_ghost.png")));
            images.put('6', ImageIO.read(new File("assets/illustrations/mage_ghost.png")));
            images.put('7', ImageIO.read(new File("assets/illustrations/rogue_ghost.png")));
            images.put('8', ImageIO.read(new File("assets/illustrations/hunter_ghost.png")));

            images.put('F', ImageIO.read(new File("assets/illustrations/fight_cloud.png")));

            images.put('s', ImageIO.read(new File("assets/illustrations/goblin_sword.png")));
            images.put('k', ImageIO.read(new File("assets/illustrations/goblin_bow.png")));
            images.put('q', ImageIO.read(new File("assets/illustrations/dark_mage_knife.png")));
            images.put('z', ImageIO.read(new File("assets/illustrations/dark_mage_spellbook.png")));
            images.put('b', ImageIO.read(new File("assets/illustrations/kobold_sorcerer.png")));
            images.put('g', ImageIO.read(new File("assets/illustrations/kobold_spear.png")));
            images.put('w', ImageIO.read(new File("assets/illustrations/skeleton_spear.png")));

            images.put('M', ImageIO.read(new File("assets/illustrations/boss.png")));
            images.put('C', ImageIO.read(new File("assets/illustrations/boss.png")));
            images.put('K', ImageIO.read(new File("assets/illustrations/boss.png")));

            images.put('B', ImageIO.read(new File("assets/illustrations/trap_silver.png")));
            images.put('Q', ImageIO.read(new File("assets/illustrations/trap_gold.png")));
            images.put('D', ImageIO.read(new File("assets/illustrations/trap_bronze.png")));

        } catch (Exception e) {
            throw new RuntimeException("Failed to load images", e);
        }
    }

    private void setupKeyBindings() {
        bindKey("W", 'w');
        bindKey("S", 's');
        bindKey("A", 'a');
        bindKey("D", 'd');
        bindKey("E", 'e');
        bindKey("Q", 'q');
    }

    private void bindKey(String key, char action) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        getActionMap().put(key, new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                controller.guiPlayerAction(String.valueOf(action));
                window.refresh(controller.getBoard());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Position playerPos = board.getPlayer().getPosition();
        int visibleRows = Math.min(VIEW_HEIGHT, board.getBoardHeight());
        int visibleCols = Math.min(VIEW_WIDTH, board.getBoardWidth());

        int startRow = playerPos.getX() - visibleRows / 2;
        int startCol = playerPos.getY() - visibleCols / 2;

        startRow = Math.max(0, Math.min(startRow, board.getBoardHeight() - visibleRows));
        startCol = Math.max(0, Math.min(startCol, board.getBoardWidth() - visibleCols));

        for (Tile tile : board.getTiles()) {
            int row = tile.getPosition().getX();
            int col = tile.getPosition().getY();

            if (row < startRow || row >= startRow + visibleRows ||
                    col < startCol || col >= startCol + visibleCols) {
                continue;
            }

            int x = (col - startCol) * TILE_SIZE;
            int y = (row - startRow) * TILE_SIZE;

            g.drawImage(ground, x, y, TILE_SIZE, TILE_SIZE, null);

            BufferedImage image = getImageForTile(tile);
            if (image != null) {
                g.drawImage(image, x, y, TILE_SIZE, TILE_SIZE, null);
            }
        }

        BufferedImage cloud = images.get('F');
        if (cloud != null) {
            for (Position position : fightPositions) {
                int row = position.getX();
                int col = position.getY();

                if (row < startRow || row >= startRow + visibleRows ||
                        col < startCol || col >= startCol + visibleCols) {
                    continue;
                }

                int x = (col - startCol) * TILE_SIZE;
                int y = (row - startRow) * TILE_SIZE;

                g.drawImage(cloud, x, y, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }

    private BufferedImage getImageForTile(Tile tile) {
        if (tile instanceof Warrior) {
            return tile.getTile() == 'X' ? images.get('5') : images.get('1');
        }

        if (tile instanceof Mage) {
            return tile.getTile() == 'X' ? images.get('6') : images.get('2');
        }

        if (tile instanceof Rogue) {
            return tile.getTile() == 'X' ? images.get('7') : images.get('3');
        }

        if (tile instanceof Hunter) {
            return tile.getTile() == 'X' ? images.get('8') : images.get('4');
        }

        return images.get(tile.getTile());
    }

    public void showFightClouds(List<Position> positions) {
        if (positions == null || positions.isEmpty()) {
            return;
        }

        this.fightPositions = new ArrayList<>(positions);
        repaint();

        new Timer(400, e -> {
            fightPositions.clear();
            repaint();
            ((Timer) e.getSource()).stop();
        }).start();
    }

}