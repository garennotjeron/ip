package dk;

import dk.storage.Storage;
import dk.ui.Ui;

public class DK {

    private static final String FILEPATH = "data";
    private static final String FILENAME = FILEPATH + "/allTasks.txt";

    public static void main(String[] args) {

        Storage storage = new Storage(FILEPATH,FILENAME);
        Ui ui = new Ui(storage);
        ui.start();
    }
}

