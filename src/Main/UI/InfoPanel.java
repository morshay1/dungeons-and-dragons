package main.ui;

import javax.swing.*;
import java.awt.*;

import main.Board;
import players.Player;

public class InfoPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel statsLabel;
    private JProgressBar healthBar;

    public InfoPanel(Board board) {
        setPreferredSize(new Dimension(1000, 95));
        setLayout(new BorderLayout(15, 5));
        setBackground(new Color(186, 150, 95));

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 45, 20), 4),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Serif", Font.BOLD, 26));
        nameLabel.setForeground(new Color(35, 20, 10));

        statsLabel = new JLabel();
        statsLabel.setFont(new Font("Serif", Font.BOLD, 17));
        statsLabel.setForeground(new Color(35, 20, 10));

        healthBar = new JProgressBar();
        healthBar.setStringPainted(true);
        healthBar.setPreferredSize(new Dimension(250, 22));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(nameLabel);
        textPanel.add(statsLabel);

        add(textPanel, BorderLayout.CENTER);
        add(healthBar, BorderLayout.EAST);

        updateInfo(board);
    }

    public void updateInfo(Board board) {
        Player p = board.getPlayer();

        nameLabel.setText(p.getName());

        statsLabel.setText(
                "Attack: " + p.getAttackPoints()
                        + " | Defense: " + p.getDefensePoints()
                        + " | Level: " + p.getLevel()
                        + " | XP: " + p.getExperience() + "/" + (p.getLevel() * 50)
        );

        healthBar.setMaximum(p.getHealthPool());
        healthBar.setValue(p.getHealthAmount());
        healthBar.setString("HP: " + p.getHealthAmount() + "/" + p.getHealthPool());
    }
}