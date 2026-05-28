package Main.UI;

import javax.swing.*;

import Main.TileFactory;

import java.awt.*;
import java.util.List;
import Players.Player;

public class StartPanel extends JPanel {
    public StartPanel(GameWindow window) {
        setPreferredSize(new Dimension(700, 500));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Select Your Player");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title);

        add(Box.createVerticalStrut(30));

        TileFactory tileFactory = new TileFactory();
        List<Player> players = tileFactory.listPlayers();

        for (Player player : players) {
            JButton button = new JButton("<html>" + player.describe().replace("\n", "<br>") + "</html>");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(500, 90));

            button.addActionListener(e -> window.startGame(player));

            add(button);
            add(Box.createVerticalStrut(15));
        }
    }
}
