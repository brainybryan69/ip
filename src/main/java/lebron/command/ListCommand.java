package lebron.command;

import java.time.LocalDate;

import lebron.common.LeBronException;
import lebron.storage.FileManager;
import lebron.task.TaskList;
import lebron.ui.Ui;
import lebron.util.DateTimeParser;

/**
 * Command to display all tasks in the task list.
 * Shows numbered list of all tasks with their status and details.
 * Can also display tasks for a specific date.
 */
public class ListCommand extends Command {
    private String dateString;

    /**
     * Creates a ListCommand to display all tasks.
     */
    public ListCommand() {
        this.dateString = null;
    }

    /**
     * Creates a ListCommand to display tasks on a specific date.
     *
     * @param dateString the date string in DD MM YYYY format
     */
    public ListCommand(String dateString) {
        this.dateString = dateString;
    }

    /**
     * Executes the list command by displaying tasks.
     * If no date is specified, shows all tasks.
     * If a date is specified, shows only tasks on that date.
     *
     * @param taskList the task list to display
     * @param ui the UI component for displaying the tasks
     * @param storage the storage component (not used for listing)
     * @return true to continue program execution
     */
    @Override
    public boolean execute(TaskList taskList, Ui ui, FileManager storage) {
        if (dateString == null) {
            // Show all tasks
            ui.showTaskList(taskList);
        } else {
            try {
                // Parse the date and show tasks on that date
                LocalDate targetDate = DateTimeParser.parseDateFromDDMMYYYY(dateString);
                TaskList filteredTasks = taskList.getTasksOnDate(targetDate);
                ui.showTasksOnDate(filteredTasks, targetDate);
            } catch (LeBronException e) {
                ui.showError(e.getMessage());
            }
        }
        return true;
    }
}
