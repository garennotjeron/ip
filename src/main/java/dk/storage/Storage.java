package dk.storage;

import dk.tasks.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {

    private TaskList allTasks;
    private Path fileName;

    public Storage (String path, String name) {
        Path filePath = Paths.get(path);
        Path fileName = Paths.get(name);

        if (Files.notExists(filePath)) {
            try {
                Files.createDirectory(filePath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if (Files.notExists(fileName)) {
            try {
                Files.createFile(fileName);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        this.fileName = fileName;
        this.allTasks = loadTasks();
    }

    public TaskList getAllTasks() {
        return this.allTasks;
    }

    public TaskList loadTasks() {
        List<String> fromFile = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        try {
            fromFile = Files.readAllLines(this.fileName);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        for (String line : fromFile) {
            try {
                String[] splitted = line.split(",");
                String category = splitted[0];
                boolean isCompleted = splitted[1].equals("1");
                if (category.equals("T")) {
                    taskList.add(new Todo(splitted[2], isCompleted));
                } else if (category.equals("D")) {
                    LocalDate by = LocalDate.parse(splitted[3].trim());
                    taskList.add(new Deadline(splitted[2], isCompleted, by));
                } else if (category.equals("E")) {
                    LocalDate start = LocalDate.parse(splitted[3].trim());
                    LocalDate end = LocalDate.parse(splitted[4].trim());
                    taskList.add(new Event(splitted[2], isCompleted, start, end));
                }
            } catch (Exception e) {
                System.out.println("Skipping line with invalid format: " + line);
            }
        }
        return new TaskList(taskList);
    }

    public void saveCurrentTasks() {
        List<String> currentTasks = new ArrayList<>();
        for (Task t : this.allTasks.getTasks()) {
            currentTasks.add(t.convertToFileFormat());
        }
        try {
            Files.write(fileName, currentTasks);
        } catch (IOException e ) {
            System.out.println(e.getMessage());
        }
    }

}
