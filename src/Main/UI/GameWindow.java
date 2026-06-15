package main.ui;

import javax.swing.*;
import java.awt.*;
import players.Player;
import main.Board;
import main.GameController;

public class GameWindow extends JFrame {
    private String levelsPath;
    private GameController controller;
    private InfoPanel infoPanel;

    public GameWindow(String levelsPath) {
        this.levelsPath = levelsPath;

        setTitle("Dungeons and Dragons Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showPlayerSelection();

        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void showPlayerSelection() {
        setContentPane(new StartPanel(this));
        pack();
        setLocationRelativeTo(null);
    }

    public void startGame(Player player) {
        controller = new GameController(levelsPath);
        controller.startWithPlayer(player, this);
    }

    public void showGame(Board board) {
        GameBackgroundPanel mainPanel = new GameBackgroundPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GamePanel gamePanel = new GamePanel(controller, board);
        infoPanel = new InfoPanel(board);

        JPanel boardWrapper = new JPanel(new GridBagLayout());
        boardWrapper.setOpaque(false);
        boardWrapper.add(gamePanel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(boardWrapper, BorderLayout.CENTER);

        setContentPane(mainPanel);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
    }

    public void refresh(Board board) {
        if (infoPanel != null) {
            infoPanel.updateInfo(board);
        }
        repaint();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}

class GameBackgroundPanel extends JPanel {
    private Image background;

    public GameBackgroundPanel() {
        try {
            background = javax.imageio.ImageIO.read(
                    new java.io.File("assets/illustrations/ground.png")
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load game background", e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background == null) {
            return;
        }

        int imgWidth = background.getWidth(this);
        int imgHeight = background.getHeight(this);

        for (int x = 0; x < getWidth(); x += imgWidth) {
            for (int y = 0; y < getHeight(); y += imgHeight) {
                g.drawImage(background, x, y, this);
            }
        }
    }
}