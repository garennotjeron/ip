package dk.parsers;

import dk.storage.Storage;
import dk.exceptions.DKException;
import dk.tasks.Deadline;
import dk.tasks.Event;
import dk.tasks.Task;
import dk.tasks.Todo;

import java.time.LocalDate;

/**
 * Handles the logic and makes sense of the user's input command.
 */
public class Parser {

    private Storage storage;

    public Parser (Storage storage) {
        this.storage = storage;
    }

    /**
     * Takes in the user's input and processes it as a command,
     * executing the operation as per request.
     *
     * @param input Input command provided by the user
     */
    public void executeCommand(String input) {
        if (input.equals("list")) {
            displayList();
        } else if (input.startsWith("mark ")) {
            try {
                int index = Integer.parseInt(input.substring(5).trim());
                markItem(index);
                this.storage.saveCurrentTasks();
            } catch (DKException e) {
                System.out.println(e.toString());
            }
        } else if (input.startsWith("unmark ")) {
            try {
                int index = Integer.parseInt(input.substring(7).trim());
                unmarkItem(index);
                this.storage.saveCurrentTasks();
            } catch (DKException e) {
                System.out.println(e.toString());
            }

        } else if (input.startsWith("todo ") || input.startsWith("deadline ") || input.startsWith("event ")) {
            try {
                addItem(input);
                this.storage.saveCurrentTasks();
            } catch (DKException e) {
                System.out.println(e.toString());
            }
        } else if (input.startsWith("delete ")) {
            try {
                int index = Integer.parseInt(input.substring(7).trim());
                deleteItem(index);
                this.storage.saveCurrentTasks();
            } catch (DKException e) {
                System.out.println(e.toString());
            }
        } else {
            System.out.println("Invalid input, please try again.");
        }
    }

    /**
     * Prints the list of tasks that is tagged to the Storage variable in the Parser object.
     */
    public void displayList() {
        if (this.storage.getAllTasks().isEmpty()) {
            System.out.println("There are no tasks in the list.");
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i < this.storage.getAllTasks().getSize() + 1; i++) {
            System.out.println(i + "." + this.storage.getAllTasks().getTask(i-1).toString());
        }
    }

    /**
     * Marks the corresponding item based on the index as completed.
     * @param index The index of the task to be marked as completed
     * @throws DKException If the list of tasks is found to be empty and
     * if the index given exceeds the number of items in the list
     */
    public void markItem(int index) throws DKException {
        if (this.storage.getAllTasks().isEmpty()) {
            throw new DKException("There are no tasks found in the list");
        }

        if (index <= 0 || index > this.storage.getAllTasks().getSize()) {
            throw new DKException("Please re-enter your command with a valid task number (1 - "
                    + this.storage.getAllTasks().getSize() + ")");
        }

        Task t = this.storage.getAllTasks().getTask(index-1);
        if (t.getCompletion().equals(" ")) {
            t.updateCompletion();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(index + "." + t.toString());
        } else {
            System.out.println("This task has already been marked as done");
        }
    }

    /**
     * Marks the corresponding item based on the index as not completed.
     * @param index The index of the item to be mark as not completed
     * @throws DKException If the list of tasks is found to be empty and
     * if the index given exceeds the number of items in the list
     */
    public void unmarkItem(int index) throws DKException {
        if (this.storage.getAllTasks().isEmpty()) {
            throw new DKException("There are no tasks found in the list");
        }

        if (index <= 0 || index > this.storage.getAllTasks().getSize()) {
            throw new DKException("Please re-enter your command with a valid task number (1 - "
                    + this.storage.getAllTasks().getSize() + ")");
        }

        Task t = this.storage.getAllTasks().getTask(index - 1);
        if (t.getCompletion().equals("X")) {
            t.updateCompletion();
            System.out.println("Nice! I've marked this task as not done yet:");
            System.out.println(index + "." + t.toString());
        } else {
            System.out.println("This task has already been marked as not done yet");
        }
    }

    /**
     * Creates and adds an item depending on the user input to the task list.
     * @param input The description of the task given by the user
     * @throws DKException If input given by user is invalid or is in the incorrect format
     */
    public void addItem(String input) throws DKException {
        Task newTask;
        if (input.startsWith("todo ")) {
            if (input.substring(5).isEmpty()) {
                throw new DKException("Description is missing, Format of command should match the following: 'todo {description}' " );
            }
            newTask = new Todo(input.substring(5));
        } else if (input.startsWith("deadline ")) {
            if (!input.substring(9).contains("/by")) {
                throw new DKException("Format of command should match the following: 'deadline {description} /by {deadline}' ");
            }
            String[] splitted = input.split("/by");
            if (splitted.length != 2) {
                throw new DKException("Format of command should match the following: 'deadline {description} /by {deadline}' ");
            }
            if (splitted[0].trim().isEmpty()) {
                throw new DKException("Description is missing, Format of command should match the following: 'deadline {description} /by {deadline}' ");
            }
            if (splitted[1].trim().isEmpty()) {
                throw new DKException("dk.tasks.Deadline is missing, Format of command should match the following: 'deadline {description} /by {deadline}' ");
            }
            newTask = new Deadline(splitted[0].substring(9).trim(), LocalDate.parse(splitted[1].trim()));
        } else {
            input = input.substring(6).trim();
            if (!input.contains("/from")) {
                throw new DKException("Format of command should match the following: 'event {description} /from {startDate} /to {endDate}' ");
            }

            if (!input.contains("/to")) {
                throw new DKException("Format of command should match the following: 'event {description} /from {startDate} /to {endDate}' ");
            }

            String[] splitted = input.split("/from");

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
            newTask = new Event(description, LocalDate.parse(startDate), LocalDate.parse(endDate));
        }
        this.storage.getAllTasks().addTask(newTask);
        System.out.println("Got it. I've added this task:\n" + newTask.toString());
        String s = this.storage.getAllTasks().getSize() == 1 ? "" : "s";
        System.out.println("Now you have " + this.storage.getAllTasks().getSize() + " task" + s + " in the list.");
    }

    /**
     * Deletes the corresponding item from the task list based on the index given by the user.
     * @param index The index of the item to be deleted from the task list
     * @throws DKException If there are no tasks in the list or
     * if the index given exceeds the number of tasks in the list
     */
    public void deleteItem(int index) throws DKException {
        if (this.storage.getAllTasks().isEmpty()) {
            throw new DKException("There are no tasks in the list");
        }

        if (index <= 0 || index > this.storage.getAllTasks().getSize()) {
            throw new DKException("Please re-enter your command with a valid task number (1 - "
                    + this.storage.getAllTasks().getSize() + ")");
        }
        Task t = this.storage.getAllTasks().removeTask(index - 1);
        System.out.println("Noted! I've removed this task: \n" + t.toString());
        String s = this.storage.getAllTasks().getSize() == 1 ? "" : "s";
        System.out.println("Now you have " + this.storage.getAllTasks().getSize() + " task" + s + " in the list.");
    }
}
