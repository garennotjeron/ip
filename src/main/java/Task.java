public class Task {
    private String description;

    private boolean isCompleted;

    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    public void updateCompletion() {
        this.isCompleted = !isCompleted;
    }

    public String getCompletion() {
        return isCompleted ? "X" : " ";
    }

    @Override
    public String toString() {
        return ". [" + this.getCompletion() + "] " + this.description;
    }
}
