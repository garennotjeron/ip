package dk.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline Task, which consists of a deadline that the task should be completed by.
 */
public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, boolean isCompleted, LocalDate by) {
        super(description, isCompleted);
        this.by = by;
    }

    /**
     * Returns a String representation of the Deadline object.
     * @return A String representation of the Deadline object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    /**
     * Returns a String representation of the Deadline object to be saved into a file.
     * @return A String representation of the Deadline object to be saved into a file
     */
    @Override
    public String convertToFileFormat() {
        return "D," + super.convertToFileFormat() + "," +
                this.by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
