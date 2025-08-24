package lebron;
/**
 * Command to add a new todo task to the task list.
 * Creates a ToDo task with the specified description.
 */
public class AddTodoCommand extends AddCommand {
    private String description;
    
    /**
     * Creates a new add todo command.
     * 
     * @param description the description of the todo task
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }
    
    /**
     * Creates a new ToDo task with the specified description.
     * 
     * @return the created ToDo task
     */
    @Override
    protected Task createTask() {
        return new ToDo(description);
    }
}
