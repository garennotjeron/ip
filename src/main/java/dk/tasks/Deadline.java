package dk.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String convertToFileFormat() {
        return "D," + super.convertToFileFormat() + "," +
                this.by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
