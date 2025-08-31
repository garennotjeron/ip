package dk.tasks;

/**
 * Represents a Todo Task to be completed.
 */
public class Todo extends Task {
    public Todo (String description) {
        super(description);
    }

    public Todo (String description, boolean isCompleted) {
        super(description, isCompleted);
    }

    /**
     * Returns a String representation of the Todo object.
     * @return A String representation of the Todo object
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a String representation of the Todo object to be saved into a file.
     * @return A String representation of the Todo object to be saved into a file
     */
    @Override
    public String convertToFileFormat() {
        return "T," + super.convertToFileFormat();
    }
}
