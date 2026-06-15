package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import main.Board;
import main.GameController;
import tiles.Tile;
import players.Warrior;
import players.Mage;
import players.Rogue;
import players.Hunter;

public class GamePanel extends JPanel {
    private static final int TILE_SIZE = 120;

    private GameController controller;
    private Board board;

    private BufferedImage ground;
    private Map<Character, BufferedImage> images;
    private GameWindow window;

    public GamePanel(GameController controller, Board board, GameWindow window) {
        this.controller = controller;
        this.board = board;
        this.window = window;

        setPreferredSize(new Dimension(
                board.getBoardWidth() * TILE_SIZE,
                board.getBoardHeight() * TILE_SIZE));
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

        for (Tile tile : board.getTiles()) {
            int x = tile.getPosition().getY() * TILE_SIZE;
            int y = tile.getPosition().getX() * TILE_SIZE;

            g.drawImage(ground, x, y, TILE_SIZE, TILE_SIZE, null);

            BufferedImage image = getImageForTile(tile);
            if (image != null) {
                g.drawImage(image, x, y, TILE_SIZE, TILE_SIZE, null);
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

}