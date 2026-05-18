package Main;

public class Main {
    public static void main(String[] args) {
        String levelsPath = "levels";
        GameController gameController = new GameController(levelsPath);
        gameController.start();
    }
}
