package lebron.command;

import lebron.task.*;
import lebron.ui.Ui;
import lebron.storage.FileManager;
import lebron.common.*;
import lebron.util.DateTimeParser;
import java.time.LocalDate;
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
