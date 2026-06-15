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
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GamePanel gamePanel = new GamePanel(controller, board);
        infoPanel = new InfoPanel(board);

        JPanel boardWrapper = new JPanel(new GridBagLayout());
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