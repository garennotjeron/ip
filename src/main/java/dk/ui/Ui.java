package dk.ui;

import java.util.Scanner;

import dk.storage.Storage;
import dk.parsers.Parser;


/**
 * Deals with interactions involving the user and handles the UI of DK Chatbot.
 */
public class Ui {

    private final Scanner scanner;
    private final Parser parser;

    public Ui(Storage storage){
        this.scanner = new Scanner(System.in);
        this.parser = new Parser(storage);
    }

    /**
     * Prompts the user for their input. This process will loop until the user sends "bye" as their input.
     */
    public void beginInput() {
        String input = "";
        while (!input.equals("bye")) {
            input = this.scanner.nextLine();
            printLine();
            String commandOutput = parser.executeCommand(input);
            System.out.println(commandOutput);
            printLine();
        }

    }

    /**
     * Prints out a line for separation purposes.
     */
    public void printLine() {
        System.out.println("__________________________________");
    }

    /**
     * Prints out the introduction message to the user.
     */
    public void printIntro() {
        printLine();
        System.out.println("Hello! I'm DK\nWhat can I do for you?");
        printLine();
    }
}
