package Main.UI;

import javax.swing.*;
import java.awt.*;

import Main.Board;

public class InfoPanel extends JPanel {
    private JTextArea infoArea;

    public InfoPanel(Board board) {
        setPreferredSize(new Dimension(300, 500));
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Player Info");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        add(title, BorderLayout.NORTH);
        add(infoArea, BorderLayout.CENTER);

        updateInfo(board);
    }

    public void updateInfo(Board board) {
        infoArea.setText(board.getPlayer().describe());
    }
}