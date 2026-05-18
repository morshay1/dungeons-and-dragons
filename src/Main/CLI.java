package Main;

import java.util.Scanner;

import Messages.MessageCallback;
import Messages.InputReader;

public class CLI {
    private final Scanner scanner;
    public MessageCallback message;
    public InputReader inputReader;

    public CLI() {
        this.scanner = new Scanner(System.in);
        message = (x) -> display(x);
        inputReader = () -> readInput();
    }

    public void display(String output) {
        System.out.println(output);
    }

    public String readInput() {
        return scanner.nextLine();
    }
}