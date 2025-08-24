package lebron.task;

import lebron.common.TaskType;
import lebron.common.TaskStatus;
import lebron.common.LeBronException;
import lebron.util.DateTimeParser;
import java.time.LocalDateTime;

/**
 * A task that needs to be completed by a specific time or date.
 * Perfect for assignments, appointments, or anything with a due date.
 */
public class Deadline extends Task {
    private LocalDateTime by;
    
    /**
     * Creates a new deadline task with a date/time string.
     * 
     * @param description what needs to be done
     * @param byString when it needs to be done by (format: yyyy-mm-dd HHmm)
     * @throws LeBronException if the date format is invalid
     */
    public Deadline(String description, String byString) throws LeBronException {
        super(description, TaskType.DEADLINE);
        this.by = DateTimeParser.parseDateTime(byString);
    }
    
    /**
     * Creates a new deadline task with a LocalDateTime.
     * Used internally for loading from file storage.
     * 
     * @param description what needs to be done
     * @param by when it needs to be done by
     */
    public Deadline(String description, LocalDateTime by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }
    
    /**
     * Gets the full description including the deadline.
     * Shows when this task needs to be completed in readable format.
     * 
     * @return description with deadline like "submit report (by: Dec 02 2019 6:00PM)"
     */
    @Override
    public String getFullDescription() {
        return description + " (by: " + DateTimeParser.formatForDisplay(by) + ")";
    }
    
    /**
     * Gets the deadline as LocalDateTime.
     * Used by FileManager for serialization.
     * 
     * @return the deadline date and time
     */
    public LocalDateTime getBy() {
        return by;
    }
}
