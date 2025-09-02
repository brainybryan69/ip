package lebron.task;

import java.time.LocalDateTime;

import lebron.common.LeBronException;
import lebron.common.TaskType;
import lebron.util.DateTimeParser;

/**
 * A task that happens during a specific time period.
 * Great for meetings, classes, or any activity with a start and end time.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates a new event task with date/time strings.
     *
     * @param description what the event is abou
     * @param fromString when the event starts (format: yyyy-mm-dd HHmm)
     * @param toString when the event ends (format: yyyy-mm-dd HHmm)
     * @throws LeBronException if the date formats are invalid
     */
    public Event(String description, String fromString, String toString) throws LeBronException {
        super(description, TaskType.EVENT);
        this.from = DateTimeParser.parseDateTime(fromString);
        this.to = DateTimeParser.parseDateTime(toString);
    }

    /**
     * Creates a new event task with LocalDateTime objects.
     * Used internally for loading from file storage.
     *
     * @param description what the event is abou
     * @param from when the event starts
     * @param to when the event ends
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    /**
     * Gets the full description including the time period.
     * Shows when this event starts and ends in readable format.
     *
     * @return description with timing like "team meeting (from: Dec 02 2019 2:00PM to: Dec 02 2019 4:00PM)"
     */
    @Override
    public String getFullDescription() {
        return description + " (from: " + DateTimeParser.formatForDisplay(from) + " to: " + DateTimeParser.formatForDisplay(to) + ")";
    }

    /**
     * Gets the start time as LocalDateTime.
     * Used by FileManager for serialization and ON command filtering.
     *
     * @return the event start date and time
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Gets the end time as LocalDateTime.
     * Used by FileManager for serialization.
     *
     * @return the event end date and time
     */
    public LocalDateTime getTo() {
        return to;
    }
}
