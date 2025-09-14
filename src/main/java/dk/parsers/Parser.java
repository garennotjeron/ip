package dk.parsers;

import java.time.LocalDate;

import dk.exceptions.DKException;
import dk.storage.Storage;
import dk.tasks.Deadline;
import dk.tasks.Event;
import dk.tasks.Task;
import dk.tasks.TaskList;
import dk.tasks.Todo;


/**
 * Handles the logic and makes sense of the user's input command.
 */
public class Parser {

    private final Storage storage;

    public Parser (Storage storage) {
        assert storage != null : "Storage parameter cannot be null";
        this.storage = storage;
    }

    /**
     * Takes in the user's input and processes it as a command,
     * executing the operation as per request.
     *
     * @param input Input command provided by the user
     */
    public String executeCommand(String input) {
        String output = "";
        if (input.equals("list")) {
            output = displayList();
        } else if (input.startsWith("mark ")) {
            try {
                int index = Integer.parseInt(input.substring(5).trim());
                output = markItem(index);
                this.storage.saveCurrentTasks();
            } catch (DKException e) {
                System.out.println(e.toString());
            }
        } else if (input.startsWith("unmark ")) {
            try {
                int index = Integer.parseInt(input.substring(7).trim());
                output = unmarkItem(index);
                this.storage.saveCurrentTasks();
            } catch (DKException e) {
                System.out.println(e.toString());
            }

        } else if (input.startsWith("todo ") || input.startsWith("deadline ") || input.startsWith("event ")) {
            try {
                output = addItem(input);
                this.storage.saveCurrentTasks();
            } catch (DKException e) {
                System.out.println(e.toString());
            }
        } else if (input.startsWith("delete ")) {
            try {
                int index = Integer.parseInt(input.substring(7).trim());
                output = deleteItem(index);
                this.storage.saveCurrentTasks();
            } catch (DKException e) {
                System.out.println(e.toString());
            }
        } else if (input.startsWith("find ")) {
            try {
                output = findItems(input);
            } catch (DKException e) {
                System.out.println(e.toString());
            }
        } else if (input.equals("bye")) {
            output = "Bye byeee, hope to see you again";
        } else {
            output = "Invalid input, please try again.";
        }

        return output;
    }

    /**
     * Prints the list of tasks that is tagged to the Storage variable in the Parser object.
     * @return A String representation of all tasks in the list
     */
    public String displayList() {
        if (this.storage.getAllTasks().isEmpty()) {
            return "There are no tasks in the list.";
        }
        StringBuilder output = new StringBuilder(String.valueOf("Here are the tasks in your list:\n"));
        for (int i = 1; i < this.storage.getAllTasks().getSize() + 1; i++) {
            output.append(i).append(".").append(this.storage.getAllTasks().getTask(i - 1).toString());
            if (i < this.storage.getAllTasks().getSize()) {
                output.append("\n");
            }
        }
        return output.toString();
    }

    /**
     * Marks the corresponding item based on the index as completed.
     * @param index The index of the task to be marked as completed
     * @throws DKException If the list of tasks is found to be empty and
     * if the index given exceeds the number of items in the list
     * @return A String representation indicating the status of the markItem Command
     */
    public String markItem(int index) throws DKException {
        String output = "";
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
            output = "Nice! I've marked this task as done:\n" + index + "." + t.toString() ;
        } else {
            output = "This task has already been marked as done";
        }
        return output;
    }

    /**
     * Marks the corresponding item based on the index as not completed.
     * @param index The index of the item to be mark as not completed
     * @throws DKException If the list of tasks is found to be empty and
     * if the index given exceeds the number of items in the list
     * @return A String representation indicating the status of the unmarkItem Command
     */
    public String unmarkItem(int index) throws DKException {
        if (this.storage.getAllTasks().isEmpty()) {
            throw new DKException("There are no tasks found in the list");
        }

        if (index <= 0 || index > this.storage.getAllTasks().getSize()) {
            throw new DKException("Please re-enter your command with a valid task number (1 - "
                    + this.storage.getAllTasks().getSize() + ")");
        }

        Task t = this.storage.getAllTasks().getTask(index - 1);
        String output;
        if (t.getCompletion().equals("X")) {
            t.updateCompletion();
            output = "Nice! I've marked this task as not done yet:\n" + index + "." + t.toString();
        } else {
            output ="This task has already been marked as not done yet";
        }
        return output;
    }

    /**
     * Creates and adds an item depending on the user input to the task list.
     * @param input The description of the task given by the user
     * @throws DKException If input given by user is invalid or is in the incorrect format
     * @return A String representation indicating the status of the addItem Command
     */
    public String addItem(String input) throws DKException {
        Task newTask;
        if (input.startsWith("todo ")) {
            if (input.substring(5).isEmpty()) {
                throw new DKException("Format of command should match the following: 'todo {description}' " );
            }
            newTask = new Todo(input.substring(5));
        } else if (input.startsWith("deadline ")) {
            String deadlineFormatError = "Format of command should match the following:" +
                    " 'deadline {description} /by {deadline}' ";
            if (!input.substring(9).contains("/by")) {
                throw new DKException(deadlineFormatError);
            }
            String[] splitted = input.split("/by");
            if (splitted.length != 2 || splitted[0].trim().isEmpty() || splitted[1].trim().isEmpty()) {
                throw new DKException(deadlineFormatError);
            }
            newTask = new Deadline(splitted[0].substring(9).trim(), LocalDate.parse(splitted[1].trim()));
        } else {
            String eventFormatError = "Format of command should match the following:" +
                    " 'event {description} /from {startDate} /to {endDate}' ";

            input = input.substring(6).trim();
            if (!input.contains("/from") || !input.contains("/to")) {
                throw new DKException(eventFormatError);
            }
            String[] splitted = input.split("/from");
            if (splitted.length != 2 || splitted[1].trim().isEmpty()) {
                throw new DKException(eventFormatError);
            }
            String description = splitted[0].trim();
            if (description.isEmpty()) {
                throw new DKException(eventFormatError);
            }
            splitted = splitted[1].split("/to");
            if (splitted.length != 2) {
                throw new DKException(eventFormatError);
            }
            String startDate = splitted[0].trim();
            String endDate = splitted[1].trim();
            if (startDate.isEmpty() || endDate.isEmpty()) {
                throw new DKException(eventFormatError);
            }
            newTask = new Event(description, LocalDate.parse(startDate), LocalDate.parse(endDate));
        }
        this.storage.getAllTasks().addTask(newTask);
        String output = "Got it. I've added this task:\n" + newTask.toString() + "\n";
        String plural = this.storage.getAllTasks().getSize() == 1 ? "" : "s";
        output += "Now you have " + this.storage.getAllTasks().getSize() + " task" + plural + " in the list.";
        return output;
    }

    /**
     * Deletes the corresponding item from the task list based on the index given by the user.
     * @param index The index of the item to be deleted from the task list
     * @throws DKException If there are no tasks in the list or
     * if the index given exceeds the number of tasks in the list
     * @return A String representation indicating the status of the deleteItem Command
     */
    public String deleteItem(int index) throws DKException {
        if (this.storage.getAllTasks().isEmpty()) {
            throw new DKException("There are no tasks in the list");
        }

        if (index <= 0 || index > this.storage.getAllTasks().getSize()) {
            throw new DKException("Please re-enter your command with a valid task number (1 - "
                    + this.storage.getAllTasks().getSize() + ")");
        }
        Task t = this.storage.getAllTasks().removeTask(index - 1);
        String output = "Noted! I've removed this task: \n" + t.toString() + "\n";
        String plural = this.storage.getAllTasks().getSize() == 1 ? "" : "s";
        output += "Now you have " + this.storage.getAllTasks().getSize() + " task" + plural + " in the list.";
        return output;
    }

    /**
     * Finds the tasks in the list that have the keyword in their description.
     * @param input The input keyed in by the user
     * @throws DKException If the keyword is blank or if there are no tasks in the list that match the given keyword
     * @return A String representation indicating the status of the findItems Command
     */
    public String findItems(String input) throws DKException{
        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new DKException("No keyword given, please try again with the following output:"
                    + "find {keyword}");
        }
        TaskList includesKeyword = new TaskList(this.storage.getAllTasks().filterTasks(keyword));
        if (includesKeyword.isEmpty()) {
            throw new DKException("There were no tasks that match your keyword, please try again.");
        }
        String output = "Here are the matching tasks in your list:\n";
        output += includesKeyword.toString();
        return output;
    }
}
