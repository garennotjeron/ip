package dk;

import dk.storage.Storage;
import dk.ui.Ui;

public class DK {

    private static final String FILE_PATH = "data";
    private static final String FILE_NAME = FILE_PATH + "/allTasks.txt";

    public static void main(String[] args) {
        Storage storage = new Storage(FILE_PATH, FILE_NAME);
        Ui ui = new Ui(storage);
        ui.start();
    }
}

