package lebron;
/**
 * Command to add a new event task to the task list.
 * Creates an Event task with the specified description, start time, and end time.
 */
public class AddEventCommand extends AddCommand {
    private String description;
    private String from;
    private String to;
    
    /**
     * Creates a new add event command.
     * 
     * @param description the description of the event task
     * @param from the start date/time string
     * @param to the end date/time string
     */
    public AddEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }
    
    /**
     * Creates a new Event task with the specified description and time range.
     * 
     * @return the created Event task
     * @throws LeBronException if the date formats are invalid
     */
    @Override
    protected Task createTask() throws LeBronException {
        return new Event(description, from, to);
    }
}
