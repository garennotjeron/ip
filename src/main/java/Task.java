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
        return this.isCompleted + "," + this.description;
    }
}
