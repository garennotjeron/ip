public class Event extends Task{
    private String from;
    private String to;

    public Event (String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event (String description, boolean isCompleted, String from, String to) {
        super(description, isCompleted);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }

    @Override
    public String convertToFileFormat() {
        return "E," + super.convertToFileFormat() + "," + this.from + "," + this.to;
    }
}
