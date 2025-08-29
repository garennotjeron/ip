import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    private LocalDate from;
    private LocalDate to;

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

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + this.from.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) +
                " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String convertToFileFormat() {
        return "E," + super.convertToFileFormat() + "," +
                this.from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                "," + this.to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
