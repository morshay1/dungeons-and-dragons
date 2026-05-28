package Main.UI;

import javax.swing.*;
import java.awt.*;
import Players.Player;
import Main.Board;
import Main.GameController;

public class GameWindow extends JFrame {
    private String levelsPath;
    private GameController controller;
    private InfoPanel infoPanel;

    public GameWindow(String levelsPath) {
        this.levelsPath = levelsPath;

        setTitle("Dungeons and Dragons Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        showPlayerSelection();

        setLocationRelativeTo(null);
        setVisible(true);
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
        JPanel mainPanel = new JPanel(new BorderLayout());

        GamePanel gamePanel = new GamePanel(controller, board);
        infoPanel = new InfoPanel(board);

        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);

        gamePanel.requestFocusInWindow();
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