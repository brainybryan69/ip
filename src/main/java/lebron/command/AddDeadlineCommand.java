package lebron.command;

import lebron.task.*;
import lebron.ui.Ui;
import lebron.storage.FileManager;
import lebron.common.*;
import lebron.util.DateTimeParser;
import java.time.LocalDate;
/**
 * Command to add a new deadline task to the task list.
 * Creates a Deadline task with the specified description and due date.
 */
public class AddDeadlineCommand extends AddCommand {
    private String description;
    private String by;
    
    /**
     * Creates a new add deadline command.
     * 
     * @param description the description of the deadline task
     * @param by the deadline date/time string
     */
    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }
    
    /**
     * Creates a new Deadline task with the specified description and due date.
     * 
     * @return the created Deadline task
     * @throws LeBronException if the date format is invalid
     */
    @Override
    protected Task createTask() throws LeBronException {
        return new Deadline(description, by);
    }
}
