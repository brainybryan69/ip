public enum ErrorType {
    EMPTY_TODO("The description of a todo cannot be empty."),
    EMPTY_DEADLINE("The description of a deadline cannot be empty."),
    EMPTY_EVENT("The description of an event cannot be empty."),
    INVALID_TASK_NUMBER("Invalid task number."),
    MISSING_DEADLINE_FORMAT("Please specify a deadline with /by"),
    MISSING_EVENT_FORMAT("Please specify event time with /from and /to"),
    UNKNOWN_COMMAND("Sorry! I don't know what that means :(");
    
    private final String message;
    
    ErrorType(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}