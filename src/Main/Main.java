package main;

import javax.swing.SwingUtilities;
import main.ui.GameWindow;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow("levels"));
    }
}