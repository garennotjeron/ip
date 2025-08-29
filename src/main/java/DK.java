import java.io.IOException;
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
        printLine();
        printIntro();
        echoInput();
    }

    public static void printIntro() {
        System.out.println("Hello! I'm DK\nWhat can I do for you?");
        printLine();
    }

    public static void printEnding() {
        System.out.println("Bye byeee. Hope to see you again soon!");
        printLine();
    }

    public static void printLine() {
        System.out.println("__________________________________");
    }

    public static void echoInput() {
        String userInput = "";
        Scanner scanner = new Scanner(System.in);
        setup();
        loadTasks();
        while (!userInput.equals("bye")) {
            userInput = scanner.nextLine();
            printLine();
            if (userInput.equals("bye")) {
                printEnding();
            } else if (userInput.equals("list")) {
                displayList();
            } else if (userInput.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(5).trim());
                    markItem(index);
                } catch (DKException e) {
                    System.out.println(e.toString());
                }

            } else if (userInput.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(7).trim());
                    unmarkItem(index);
                } catch (DKException e) {
                    System.out.println(e.toString());
                }
            } else if (userInput.startsWith("todo ") || userInput.startsWith("deadline ") || userInput.startsWith("event ")) {
                try {
                    addItem(userInput);
                } catch (DKException e) {
                    System.out.println(e.toString());
                }
            } else if (userInput.startsWith("delete ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(7).trim());
                    deleteItem(index);
                } catch (DKException e) {
                    System.out.println(e.toString());
                }
            }
            else {
                System.out.println("Invalid input, please try again");
            }
        }
    }

    public static void displayList() {
        if (allTasks.isEmpty()) {
            System.out.println("There are no tasks in the list.");
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i < allTasks.size() + 1; i++) {
            System.out.println(i + "." + allTasks.get(i-1).toString());
        }
        printLine();
    }

    public static void markItem(int index) throws DKException{
        if (allTasks.isEmpty()) {
            throw new DKException("There are no tasks found in the list");
        }

        if (index <= 0 || index > allTasks.size()) {
            throw new DKException("Please re-enter your command with a valid task number (1 - " + allTasks.size() + ")");
        }

        Task t = allTasks.get(index - 1);
        if (t.getCompletion().equals(" ")) {
            t.updateCompletion();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(index + "." + t.toString());
        } else {
            System.out.println("This task has already been marked as done");
        }
        saveCurrentTasks();
        printLine();
    }

    public static void unmarkItem(int index) throws DKException{

        if (allTasks.isEmpty()) {
            throw new DKException("There are no tasks found in the list");
        }

        if (index <= 0 || index > allTasks.size()) {
            throw new DKException("Please re-enter your command with a valid task number (1 - " + allTasks.size() + ")");
        }

        Task t = allTasks.get(index - 1);
        if (t.getCompletion().equals("X")) {
            t.updateCompletion();
            System.out.println("Nice! I've marked this task as not done yet:");
            System.out.println(index + "." + t.toString());
        } else {
            System.out.println("This task has already been marked as not done yet");
        }
        saveCurrentTasks();
        printLine();
    }

    public static void addItem(String userInput) throws DKException{
        Task newTask;
        if (userInput.startsWith("todo ")) {
            if (userInput.substring(5).isEmpty()) {
                throw new DKException("Description is missing, Format of command should match the following: 'todo {description}' " );
            }
            newTask = new Todo(userInput.substring(5));
        } else if (userInput.startsWith("deadline ")) {
            if (!userInput.substring(9).contains("/by")) {
                throw new DKException("Format of command should match the following: 'deadline {description} /by {deadline}' ");
            }
            String[] splitted = userInput.split("/by");
            if (splitted.length != 2) {
                throw new DKException("Format of command should match the following: 'deadline {description} /by {deadline}' ");
            }
            if (splitted[0].trim().isEmpty()) {
                throw new DKException("Description is missing, Format of command should match the following: 'deadline {description} /by {deadline}' ");
            }
            if (splitted[1].trim().isEmpty()) {
                throw new DKException("Deadline is missing, Format of command should match the following: 'deadline {description} /by {deadline}' ");
            }
            newTask = new Deadline(splitted[0].substring(9).trim(), splitted[1].trim());
        } else {
            userInput = userInput.substring(6).trim();
            if (!userInput.contains("/from")) {
                throw new DKException("Format of command should match the following: 'event {description} /from {startDate} /to {endDate}' ");
            }

            if (!userInput.contains("/to")) {
                throw new DKException("Format of command should match the following: 'event {description} /from {startDate} /to {endDate}' ");
            }

            String[] splitted = userInput.split("/from");

            if (splitted.length != 2) {
                throw new DKException("Format of command should match the following: 'event {description} /from {startDate} /to {endDate}' ");
            }

            if (splitted[1].trim().isEmpty()) {
                throw new DKException("Missing Start and End Date, Format of command should match the following: 'event {description} /from {startDate} /to {endDate}'");
            }
            String description = splitted[0].trim();

            if (description.isEmpty()) {
                throw new DKException("Description is missing, Format of command should match the following: 'event {description} /from {startDate} /to {endDate}' ");
            }

            splitted = splitted[1].split("/to");
            if (splitted.length != 2) {
                throw new DKException("Format of command should match the following: 'event {description} /from {startDate} /to {endDate}' ");
            }
            String startDate = splitted[0].trim();
            String endDate = splitted[1].trim();

            if (startDate.isEmpty()) {
                throw new DKException("StartDate is missing, Format of command should match the following: 'event {description} /from {startDate} /to {endDate}' ");
            }
            if (endDate.isEmpty()) {
                throw new DKException("EndDate is missing, Format of command should match the following: 'event {description} /from {startDate} /to {endDate}' ");
            }

            newTask = new Event(description, startDate, endDate);
        }
        allTasks.add(newTask);
        System.out.println("Got it. I've added this task:\n" + newTask.toString());
        String s = allTasks.size() == 1 ? "" : "s";
        System.out.println("Now you have " + allTasks.size() + " task" + s + " in the list.");
        saveCurrentTasks();
        printLine();
    }

    public static void deleteItem(int index) throws DKException{
        if (allTasks.isEmpty()) {
            throw new DKException("There are no tasks in the list");
        }

        if (index <= 0 || index > allTasks.size()) {
            throw new DKException("Please re-enter your command with a valid task number (1 - " + allTasks.size() + ")");
        }
        Task t = allTasks.remove(index - 1);
        System.out.println("Noted! I've removed this task: \n" + t.toString());
        String s = allTasks.size() == 1 ? "" : "s";
        System.out.println("Now you have " + allTasks.size() + " task" + s + " in the list.");
        saveCurrentTasks();
        printLine();
    }

    public static void loadTasks() {
        List<String> fromFile = new ArrayList<>();

        try {
            fromFile = Files.readAllLines(Paths.get(FILENAME));
        } catch (IOException e) {
            System.out.println(e);
        }
        for (String line : fromFile) {
            try {
                String[] splitted = line.split(",");
                String category = splitted[0];
                boolean isCompleted = splitted[1].equals("1");
                if (category.equals("T")) {
                    allTasks.add(new Todo(splitted[2],isCompleted));
                } else if (category.equals("D")) {
                    allTasks.add(new Deadline(splitted[2], isCompleted, splitted[3]));
                } else if (category.equals("E")) {
                    allTasks.add(new Event(splitted[2], isCompleted, splitted[3], splitted[4]));
                }
            } catch (Exception e) {
                System.out.println("Skipping line with invalid format: " + line);
                printLine();
            }
        }
    }

    public static void saveCurrentTasks() {
        List<String> currentTasks = new ArrayList<>();
        for (Task t : allTasks) {
            currentTasks.add(t.convertToFileFormat());
        }
        try {
            Files.write(Paths.get(FILENAME), currentTasks);
        } catch (IOException e ) {
            System.out.println(e.getMessage());
        }
    }

    public static void setup() {
        Path filePath = Paths.get(FILEPATH);
        if (Files.notExists(filePath)) {
            try {
                Files.createDirectory(filePath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        Path file = Paths.get(FILENAME);
        if (Files.notExists(file)) {
            try {
                Files.createFile(file);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }


    }
}

