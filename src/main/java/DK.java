import java.io.IOException;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class DK {

    private static final List<Task> allTasks = new ArrayList<>(); // limit set for size of array for all user inputs
    private static final String FILEPATH = "data";
    private static final String FILENAME = FILEPATH + "/allTasks.txt";

    public static void main(String[] args) {

        Storage storage = new Storage(FILEPATH,FILENAME);
        Ui ui = new Ui(storage);
        ui.start();
    }
}

