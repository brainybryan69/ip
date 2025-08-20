/**
 * A simple task without any deadlines or time constraints.
 * Just something you need to get done whenever you have time.
 */
public class ToDo extends Task {
    
    /**
     * Creates a new todo task.
     * 
     * @param description what you need to do
     */
    public ToDo(String description) {
        super(description, TaskType.TODO);
    }
    
    /**
     * Gets the description of this todo.
     * Since todos are simple, this just returns the basic description.
     * 
     * @return the task description
     */
    @Override
    public String getFullDescription() {
        return description;
    }
}