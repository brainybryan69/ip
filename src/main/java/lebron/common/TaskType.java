package lebron.common;

/**
 * The different types of tasks you can create.
 * Each type has its own icon for easy identification.
 */
public enum TaskType {
    /** Simple tasks without any time constraints */
    TODO("[T]"),
    /** Tasks that need to be done by a specific time */
    DEADLINE("[D]"),
    /** Tasks that happen during a specific time period */
    EVENT("[E]");

    private final String icon;

    /**
     * Creates a task type with its display icon.
     *
     * @param icon the bracket symbol to show for this type
     */
    TaskType(String icon) {
        this.icon = icon;
    }

    /**
     * Gets the display icon for this task type.
     *
     * @return the icon like "[T]", "[D]", or "[E]"
     */
    public String getIcon() {
        return icon;
    }
}
