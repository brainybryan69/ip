package lebron.common;
/**
 * All the different commands that users can type.
 * Helps figure out what the user wants to do based on their input.
 */
public enum CommandType {
    /** Exit the program */
    BYE,
    /** Show all tasks */
    LIST,
    /** Mark a task as done */
    MARK,
    /** Mark a task as not done */
    UNMARK,
    /** Remove a task completely */
    DELETE,
    /** Add a simple todo task */
    TODO,
    /** Add a task with a deadline */
    DEADLINE,
    /** Add a task with start and end times */
    EVENT,
    /** Show tasks occurring on a specific date */
    ON,
    /** Find tasks containing a keyword */
    FIND,
    /** Command we don't recognize */
    UNKNOWN;

    /**
     * Figures out what command the user typed.
     * Looks at the beginning of their input to determine the command type.
     *
     * @param input what the user typed
     * @return the type of command they want to run
     */
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
        } else if (input.equals("on") || input.startsWith("on ")) {
            return ON;
        } else if (input.equals("find") || input.startsWith("find ")) {
            return FIND;
        } else {
            return UNKNOWN;
        }
    }
}
