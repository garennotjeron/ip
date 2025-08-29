package dk.parsers;

import dk.storage.Storage;
import dk.exceptions.DKException;
import dk.tasks.Deadline;
import dk.tasks.Event;
import dk.tasks.Task;
import dk.tasks.Todo;

import java.time.LocalDate;

public class Parser {

    private Storage storage;

    public Parser (Storage storage) {
        this.storage = storage;
    }

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
