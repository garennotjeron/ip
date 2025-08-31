package dk.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task object, which has a start and end date tagged to it.
 */
public class Event extends Task {
    private final LocalDate from;
    private final LocalDate to;

    public Event (String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event (String description, boolean isCompleted, LocalDate from, LocalDate to) {
        super(description, isCompleted);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a String representation of the Event object.
     * @return A String representation of the Event object
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + this.from.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) +
                " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    /**
     * Returns a String representation of the Event object to be saved into a file.
     * @return A String representation of the Event object to be saved into a file
     */
    @Override
    public String convertToFileFormat() {
        return "E," + super.convertToFileFormat() + "," +
                this.from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                "," + this.to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
