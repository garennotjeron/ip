package dk;

import dk.parsers.Parser;
import dk.storage.Storage;
import dk.ui.Main;
import dk.ui.Ui;
import javafx.application.Application;

public class DK {

    private static final String FILE_PATH = "data";
    private static final String FILE_NAME = FILE_PATH + "/allTasks.txt";
    private static Parser parser;

    public static void main(String[] args) {
        setUp();
        startGui();
    }

    public static void setUp() {
        Storage storage = new Storage(FILE_PATH, FILE_NAME);
        parser = new Parser(storage);
    }

    public static void startGui() {
        Application.launch(Main.class);
    }

    public String getResponse(String input) {
        if (input.equals("bye")) {
            System.exit(0);
        }
        return parser.executeCommand(input);
    }
}

