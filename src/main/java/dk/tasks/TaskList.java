package dk.tasks;

import java.util.List;

/**
 * Represents a list of Tasks that has to be completed, with the Tasks either being a
 * Deadline, Todo or Event task.
 */
public class TaskList {
    private final List<Task> allTasks;

    public TaskList(List<Task> allTasks) {
        this.allTasks = allTasks;
    }

    /**
     * Returns the specified Task object based on the index that is specified by the user.
     * @param index The index of the Task object in the list
     * @return The Task at the specified position
     */
    public Task getTask(int index) {
        return this.allTasks.get(index);
    }

    /**
     * Returns the current list of Tasks.
     * @return A list of the current Tasks
     */
    public List<Task> getTasks() {
        return this.allTasks;
    }

    /**
     * Adds the specified Task object into the list of Tasks.
     * @param t The Task object to be added to the list
     */
    public void addTask(Task t) {
        this.allTasks.add(t);
    }

    /**
     * Removes the specified Task object based on the index specified by the user.
     * @param index The index of the Task object to be removed from the list
     * @return The specified Task to be removed
     */
    public Task removeTask(int index) {
        return this.allTasks.remove(index);
    }


    /**
     * Returns the number of Tasks in the current list of Tasks.
     * @return An integer value representing the number of items in the list of Tasks
     */
    public int getSize() {
        return this.allTasks.size();
    }

    /**
     * Returns a boolean value representing whether the list of tasks is empty.
     * @return A Boolean value representing whether the list of tasks is empty
     */
    public boolean isEmpty() {
        return this.allTasks.isEmpty();
    }

    /**
     * Returns a String representation of the TaskList object.
     * @return A String representation of the TaskList object
     */
    @Override
    public String toString() {
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < allTasks.size() + 1; i++ ) {
            stringBuilder.append(String.format(i + "." + allTasks.get(i-1).toString() + "\n"));
        }
        return result;
    }
}
