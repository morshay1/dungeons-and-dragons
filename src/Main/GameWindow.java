package Main;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow(Board board, GameController controller) {
        setTitle("Dungeons and Dragons Game"); // Sets the text at the top of the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When you press X, the whole program stops.
        setResizable(false); // The user cannot manually resize the window.

        add(new GamePanel(board, controller)); // Puts your drawing panel inside the window.
                                   // The JFrame is the window.
                                   // The JPanel is the area inside the window where you draw the game.

        pack(); // Makes the window fit the preferred size of its contents.
        setLocationRelativeTo(null); // Centers the window on the screen.
        setVisible(true); // Actually shows the window.
    }
}
