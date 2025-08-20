/**
 * A task that happens during a specific time period.
 * Great for meetings, classes, or any activity with a start and end time.
 */
public class Event extends Task {
    private String from;
    private String to;
    
    /**
     * Creates a new event task.
     * 
     * @param description what the event is about
     * @param from when the event starts
     * @param to when the event ends
     */
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }
    
    /**
     * Gets the full description including the time period.
     * Shows when this event starts and ends.
     * 
     * @return description with timing like "team meeting (from: 2pm to: 4pm)"
     */
    @Override
    public String getFullDescription() {
        return description + " (from: " + from + " to: " + to + ")";
    }
}