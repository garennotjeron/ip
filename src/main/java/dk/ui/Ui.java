package dk.ui;

import java.util.Scanner;

import dk.storage.Storage;
import dk.parsers.Parser;


public class Ui {

    private final Scanner scanner;
    private final Parser parser;

    public Ui(Storage storage){
        this.scanner = new Scanner(System.in);
        this.parser = new Parser(storage);
    }

    public void start() {
        printIntro();
        beginInput();
    }

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

    public void printLine() {
        System.out.println("__________________________________");
    }

    public void printIntro() {
        printLine();
        System.out.println("Hello! I'm DK\nWhat can I do for you?");
        printLine();
    }

    public void printEnding() {
        printLine();
        System.out.println("Bye byeee. Hope to see you again soon!");
        printLine();
    }
}
