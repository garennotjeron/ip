package dk.tasks;

public class Task {
    private String description;

    private boolean isCompleted;

    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    public Task (String description, boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getDescription() {
        return this.description;
    }

    public void updateCompletion() {
        this.isCompleted = !isCompleted;
    }

    public String getCompletion() {
        return isCompleted ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + this.getCompletion() + "] " + this.description;
    }

    public String convertToFileFormat() {
        int completedStatus = this.isCompleted ? 1 : 0;
        return completedStatus + "," + this.description;
    }
}
