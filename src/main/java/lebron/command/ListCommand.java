package lebron.command;

import lebron.storage.FileManager;
import lebron.task.TaskList;
import lebron.ui.Ui;
/**
 * Command to display all tasks in the task list.
 * Shows numbered list of all tasks with their status and details.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param taskList the task list to display
     * @param ui the UI component for displaying the tasks
     * @param storage the storage component (not used for listing)
     * @return true to continue program execution
     */
    @Override
    public boolean execute(TaskList taskList, Ui ui, FileManager storage) {
        ui.showTaskList(taskList);
        return true;
    }
}
