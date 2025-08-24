package lebron.command;

import lebron.task.*;
import lebron.ui.Ui;
import lebron.storage.FileManager;
import lebron.common.*;
import lebron.util.DateTimeParser;
import java.time.LocalDate;
/**
 * Abstract base class for commands that add tasks to the task list.
 * Provides common functionality for adding tasks and updating storage.
 */
public abstract class AddCommand extends Command {
    
    /**
     * Adds a task to the task list and saves to storage.
     * Template method that calls createTask() to get the specific task type.
     * 
     * @param taskList the task list to add to
     * @param ui the UI component for showing confirmation
     * @param storage the storage component for saving
     * @return true to continue program execution
     * @throws LeBronException if task creation or storage fails
     */
    @Override
    public boolean execute(TaskList taskList, Ui ui, FileManager storage) throws LeBronException {
        Task task = createTask();
        taskList.addTask(task);
        ui.showTaskAdded(task, taskList.size());
        saveToStorage(taskList, storage, ui);
        return true;
    }
    
    /**
     * Creates the specific task type for this add command.
     * Implemented by concrete subclasses.
     * 
     * @return the created task
     * @throws LeBronException if task creation fails
     */
    protected abstract Task createTask() throws LeBronException;
    
    /**
     * Saves the task list to storage and handles any errors.
     * 
     * @param taskList the task list to save
     * @param storage the storage component
     * @param ui the UI component for error messages
     */
    private void saveToStorage(TaskList taskList, FileManager storage, Ui ui) {
        try {
            storage.saveTasks(taskList.getTasks());
        } catch (Exception e) {
            ui.showSaveError();
        }
    }
}
