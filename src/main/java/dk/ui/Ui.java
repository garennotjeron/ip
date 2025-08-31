package dk.ui;

import dk.parsers.Parser;
import dk.storage.Storage;

import java.util.Scanner;

/**
 * Deals with interactions involving the user and handles the UI of DK Chatbot.
 */
public class Ui {

    private Scanner scanner;
    private Parser parser;

    public Ui(Storage storage){
        this.scanner = new Scanner(System.in);
        this.parser = new Parser(storage);
    }

    /**
     * Starts up the DK Chatbot process.
     */
    public void start() {
        printIntro();
        beginInput();
    }

    /**
     * Prompts the user for their input. This process will loop until the user sends "bye" as their input.
     */
    public void beginInput() {
        String input = "";
        while (!input.equals("bye")) {
            input = this.scanner.nextLine();
            printLine();
            if (input.equals("bye")){
                printEnding();
            } else {
                parser.executeCommand(input);
                printLine();
            }
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

    /**
     * Prints out the ending message to the user.
     */
    public void printEnding() {
        printLine();
        System.out.println("Bye byeee. Hope to see you again soon!");
        printLine();
    }
}
