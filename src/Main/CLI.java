package main;

import java.util.Scanner;

import messages.MessageCallback;
import messages.InputReader;

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