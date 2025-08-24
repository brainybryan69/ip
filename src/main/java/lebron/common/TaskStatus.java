package lebron.common;
/**
 * Whether a task has been completed or not.
 * Shows up as different checkbox symbols in the task list.
 */
public enum TaskStatus {
    /** Task is completed - shows as [X] */
    DONE("[X]"),
    /** Task is not yet completed - shows as [ ] */
    NOT_DONE("[ ]");
    
    private final String icon;
    
    /**
     * Creates a task status with its checkbox icon.
     * 
     * @param icon the checkbox symbol to display
     */
    TaskStatus(String icon) {
        this.icon = icon;
    }
    
    /**
     * Gets the checkbox icon for this status.
     * 
     * @return "[X]" for done or "[ ]" for not done
     */
    public String getIcon() {
        return icon;
    }
}
