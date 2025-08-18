public enum CommandType {
    BYE,
    LIST,
    MARK,
    UNMARK,
    DELETE,
    TODO,
    DEADLINE,
    EVENT,
    UNKNOWN;
    
    public static CommandType parseCommand(String input) {
        if (input.equals("bye")) {
            return BYE;
        } else if (input.equals("list")) {
            return LIST;
        } else if (input.startsWith("mark ")) {
            return MARK;
        } else if (input.startsWith("unmark ")) {
            return UNMARK;
        } else if (input.startsWith("delete ")) {
            return DELETE;
        } else if (input.equals("todo") || input.startsWith("todo ")) {
            return TODO;
        } else if (input.equals("deadline") || input.startsWith("deadline ")) {
            return DEADLINE;
        } else if (input.equals("event") || input.startsWith("event ")) {
            return EVENT;
        } else {
            return UNKNOWN;
        }
    }
}