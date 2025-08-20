/**
 * A task that can be tracked and completed.
 * This is the parent class for all types of tasks like todos, deadlines, and events.
 */
public abstract class Task {
    protected String description;
    protected TaskStatus status;
    protected TaskType type;
    
    /**
     * Creates a new task with the given description.
     * The task starts as not done.
     * 
     * @param description what the task is about
     * @param type the kind of task (todo, deadline, or event)
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.status = TaskStatus.NOT_DONE;
        this.type = type;
    }
    
    /**
     * Gets what this task is about.
     * 
     * @return the task description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Checks if this task has been completed.
     * 
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return status == TaskStatus.DONE;
    }
    
    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        status = TaskStatus.DONE;
    }
    
    /**
     * Marks this task as not completed yet.
     */
    public void markAsNotDone() {
        status = TaskStatus.NOT_DONE;
    }
    
    /**
     * Gets the icon showing if this task is done or not.
     * 
     * @return "[X]" if done, "[ ]" if not done
     */
    public String getStatusIcon() {
        return status.getIcon();
    }
    
    /**
     * Gets the icon showing what type of task this is.
     * 
     * @return "[T]", "[D]", or "[E]" depending on task type
     */
    public String getTypeIcon() {
        return type.getIcon();
    }
    
    /**
     * Gets the complete description with any extra details.
     * Each task type adds its own formatting.
     * 
     * @return the full task description
     */
    public abstract String getFullDescription();
}