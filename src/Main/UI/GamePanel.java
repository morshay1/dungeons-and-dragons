package Main.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import Main.GameController;
import Main.Board;

import Tiles.Tile;

public class GamePanel extends JPanel {
    private static final int TILE_SIZE = 32;

    private GameController controller;
    private Board board;

    private BufferedImage ground;
    private Map<Character, BufferedImage> images;

    public GamePanel(GameController controller, Board board) {
        this.controller = controller;
        this.board = board;

        setPreferredSize(new Dimension(
                board.getBoardWidth() * TILE_SIZE,
                board.getBoardHeight() * TILE_SIZE
        ));

        setFocusable(true);
        loadImages();
        setupKeyBindings();
    }

    private void loadImages() {
        try {
            ground = ImageIO.read(new File("assets/ground.png"));

            images = new HashMap<>();
            images.put('#', ImageIO.read(new File("assets/bush.png")));
            images.put('@', ImageIO.read(new File("assets/warrior.png")));
            images.put('s', ImageIO.read(new File("assets/goblin_sword.png")));
            images.put('k', ImageIO.read(new File("assets/skeleton_spear.png")));
            images.put('m', ImageIO.read(new File("assets/mage.png")));

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
                controller.gameTick(action);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Tile tile : board.getTiles()) {
            int x = tile.getPosition().getY() * TILE_SIZE;
            int y = tile.getPosition().getX() * TILE_SIZE;

            g.drawImage(ground, x, y, TILE_SIZE, TILE_SIZE, null);

            char symbol = tile.getTile();
            BufferedImage image = images.get(symbol);

            if (image != null) {
                g.drawImage(image, x, y, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }
}