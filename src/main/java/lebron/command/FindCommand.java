package lebron.command;

import lebron.task.*;
import lebron.ui.Ui;
import lebron.storage.FileManager;
import lebron.common.*;
import lebron.util.DateTimeParser;
import java.time.LocalDate;
import java.time.LocalDate;

/**
 * Command to find and display tasks occurring on a specific date.
 * Shows all deadlines and events that match the target date.
 */
public class FindCommand extends Command {
    private String dateString;
    
    /**
     * Creates a new find command for the specified date.
     * 
     * @param dateString the date string to search for
     */
    public FindCommand(String dateString) {
        this.dateString = dateString;
    }
    
    /**
     * Executes the find command by searching for tasks on the specified date.
     * 
     * @param taskList the task list to search in
     * @param ui the UI component for displaying results
     * @param storage the storage component (not used for searching)
     * @return true to continue program execution
     * @throws LeBronException if the date format is invalid
     */
    @Override
    public boolean execute(TaskList taskList, Ui ui, FileManager storage) throws LeBronException {
        LocalDate targetDate = DateTimeParser.parseDate(dateString);
        TaskList matchingTasks = taskList.getTasksOnDate(targetDate);
        ui.showTasksOnDate(matchingTasks, targetDate);
        return true;
    }
}
