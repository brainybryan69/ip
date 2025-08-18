public class Event extends Task {
    private String from;
    private String to;
    
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }
    
    @Override
    public String getFullDescription() {
        return description + " (from: " + from + " to: " + to + ")";
    }
}