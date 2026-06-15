package main.ui;

import javax.swing.*;
import java.awt.*;

import main.Board;

public class InfoPanel extends JPanel {
    private JTextArea infoArea;

    public InfoPanel(Board board) {
        setPreferredSize(new Dimension(700, 70));
        setLayout(new BorderLayout());

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        infoArea.setRows(2);
        infoArea.setMargin(new Insets(8, 10, 8, 10));

        add(infoArea, BorderLayout.CENTER);

        updateInfo(board);
    }

    public void updateInfo(Board board) {
        infoArea.setText(board.getPlayer().describe());
    }
}