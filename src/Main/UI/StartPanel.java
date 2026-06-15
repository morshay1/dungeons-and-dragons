package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;

import main.TileFactory;
import players.Player;

public class StartPanel extends JPanel {
    private BufferedImage background;

    public StartPanel(GameWindow window) {
        try {
            background = ImageIO.read(new File("assets/illustrations/ground.png"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load start background", e);
        }

        setLayout(new GridBagLayout());

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Select Your Player");
        title.setFont(new Font("Serif", Font.BOLD, 42));
        title.setForeground(new Color(45, 25, 10));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(title);
        content.add(Box.createVerticalStrut(30));

        TileFactory factory = new TileFactory();
        List<Player> players = factory.listPlayers();

        for (Player player : players) {
            JButton button = createPlayerButton(player);
            button.addActionListener(e -> window.startGame(player));

            content.add(button);
            content.add(Box.createVerticalStrut(15));
        }

        add(content);
    }

    private JButton createPlayerButton(Player player) {
        String text = "<html><center>" +
                "<b style='font-size:22px'>" + player.getName() + "</b><br>" +
                "Health: " + player.getHealthAmount() + "/" + player.getHealthPool() +
                " | Attack: " + player.getAttackPoints() +
                " | Defense: " + player.getDefensePoints() +
                " | Level: " + player.getLevel() +
                " | XP: " + player.getExperience() +
                "</center></html>";

        JButton button = new JButton(text);

        button.setFont(new Font("Serif", Font.BOLD, 16));
        button.setForeground(new Color(35, 20, 10));
        button.setBackground(new Color(186, 150, 95));
        button.setFocusPainted(false);

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 45, 20), 3),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(900, 85));
        button.setPreferredSize(new Dimension(900, 85));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background == null) {
            return;
        }

        for (int x = 0; x < getWidth(); x += background.getWidth()) {
            for (int y = 0; y < getHeight(); y += background.getHeight()) {
                g.drawImage(background, x, y, null);
            }
        }
    }
}