package dk.tasks;

/**
 * Represents a Task object, which contains a description of the task and whether it has been completed or not.
 */
public class Task {
    private final String description;
    private boolean isCompleted;

    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    public Task (String description, boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }

    /**
     * Returns the description of the task.
     * @return A String representation of the description of the task
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Updates the task's completion status based on its previous value.
     */
    public void updateCompletion() {
        this.isCompleted = !isCompleted;
    }

    /**
     * Returns a String representation of whether the task is completed or not.
     * @return A String representation of whether the task is completed or not
     */
    public String getCompletion() {
        return isCompleted ? "X" : " ";
    }

    /**
     * Returns a String representation of the Task object.
     * @return A String representation of the Task object
     */
    @Override
    public String toString() {
        return "[" + this.getCompletion() + "] " + this.description;
    }

    /**
     * Returns a String representation of the Task object to be saved into a file.
     * @return A String representation of the Task object to be saved into a file
     */
    public String convertToFileFormat() {
        int completedStatus = this.isCompleted ? 1 : 0;
        return completedStatus + "," + this.description;
    }
}
