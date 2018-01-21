package ru.ezhov.apptestconsole;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    private static final String WORD_EXIT = "exit";

    public static void main(String[] args) {
        System.out.println("Input \"" + WORD_EXIT + "\" for close application.");
        System.out.println("Hello :)");
        printInput();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (WORD_EXIT.equals(line)) {
                System.out.println("Application close.");
                System.exit(0);
            }
            System.out.println("Your input: " + line);
            printInput();
        }
    }

    private static void printInput() {
        System.out.print(">");
    }
}
