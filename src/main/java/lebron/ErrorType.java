package lebron;
/**
 * All the different error messages the app can show.
 * Keeps all error text in one place so it's easy to change.
 */
public enum ErrorType {
    /** When user types "todo" but forgets to say what to do */
    EMPTY_TODO("The description of a todo cannot be empty."),
    /** When user types "deadline" but forgets to say what to do */
    EMPTY_DEADLINE("The description of a deadline cannot be empty."),
    /** When user types "event" but forgets to say what to do */
    EMPTY_EVENT("The description of an event cannot be empty."),
    /** When user tries to mark/unmark/delete a task that doesn't exist */
    INVALID_TASK_NUMBER("Invalid task number."),
    /** When user creates a deadline but forgets the /by part */
    MISSING_DEADLINE_FORMAT("Please specify a deadline with /by"),
    /** When user creates an event but forgets the /from and /to parts */
    MISSING_EVENT_FORMAT("Please specify event time with /from and /to"),
    /** When user types something we don't understand */
    UNKNOWN_COMMAND("Sorry! I don't know what that means :(");
    
    private final String message;
    
    /**
     * Creates an error type with its message.
     * 
     * @param message what to tell the user when this error happens
     */
    ErrorType(String message) {
        this.message = message;
    }
    
    /**
     * Gets the error message to show the user.
     * 
     * @return the helpful error message
     */
    public String getMessage() {
        return message;
    }
}
