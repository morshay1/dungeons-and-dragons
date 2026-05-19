package Main;

import javax.swing.*;

import Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
    private static final int TILE_SIZE = 100;

    private Board board;
    private GameController controller;

    private BufferedImage ground;
    private BufferedImage bush;
    private BufferedImage warrior;
    private BufferedImage mage;
    private BufferedImage goblinSword;
    private BufferedImage goblinBow;
    private BufferedImage skeletonSpear;

    public GamePanel(Board board, GameController controller) {
        this.board = board;
        this.controller = controller;

        loadImages();

        setPreferredSize(new Dimension(
                board.getBoardWidth() * TILE_SIZE,
                board.getBoardHeight() * TILE_SIZE));

        setFocusable(true);
        setupKeyBindings();
    }

    private void loadImages() {
        try {
            ground = ImageIO.read(new File("assets/ground.png"));
            bush = ImageIO.read(new File("assets/bush.png"));
            warrior = ImageIO.read(new File("assets/warrior.png"));
            mage = ImageIO.read(new File("assets/mage.png"));
            goblinBow = ImageIO.read(new File("assets/goblin_bow.png"));
            goblinSword = ImageIO.read(new File("assets/goblin_sword.png"));
            skeletonSpear = ImageIO.read(new File("assets/skeleton_spear.png"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load images", e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Tile tile : board.getTiles()) {
            int x = tile.getPosition().getY() * TILE_SIZE;
            int y = tile.getPosition().getX() * TILE_SIZE;

            g.drawImage(ground, x, y, TILE_SIZE, TILE_SIZE, null);

            switch (tile.getTile()) {
                case '#':
                    g.drawImage(bush, x, y, TILE_SIZE, TILE_SIZE, null);
                    break;
                case '@':
                    g.drawImage(warrior, x, y, TILE_SIZE, TILE_SIZE, null);
                    break;
                case 's':
                    g.drawImage(goblinBow, x, y, TILE_SIZE, TILE_SIZE, null);
                    break;
                case 'k':
                    g.drawImage(skeletonSpear, x, y, TILE_SIZE, TILE_SIZE, null);
                    break;

            }
        }
    }

    private void setupKeyBindings() {
        bindKey("W", () -> controller.guiPlayerAction("w"));
        bindKey("S", () -> controller.guiPlayerAction("s"));
        bindKey("A", () -> controller.guiPlayerAction("a"));
        bindKey("D", () -> controller.guiPlayerAction("d"));
        bindKey("E", () -> controller.guiPlayerAction("e"));
    }

    private void bindKey(String key, Runnable action) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        getActionMap().put(key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
                repaint();
            }
        });
    }
}