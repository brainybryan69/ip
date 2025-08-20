/**
 * A task that needs to be completed by a specific time or date.
 * Perfect for assignments, appointments, or anything with a due date.
 */
public class Deadline extends Task {
    private String by;
    
    /**
     * Creates a new deadline task.
     * 
     * @param description what needs to be done
     * @param by when it needs to be done by
     */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }
    
    /**
     * Gets the full description including the deadline.
     * Shows when this task needs to be completed.
     * 
     * @return description with deadline like "submit report (by: Friday 5pm)"
     */
    @Override
    public String getFullDescription() {
        return description + " (by: " + by + ")";
    }
}