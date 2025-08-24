package lebron;
/**
 * Command to unmark a task (mark as not completed).
 * Updates the task's status and saves changes to storage.
 */
public class UnmarkCommand extends Command {
    private int taskIndex;
    
    /**
     * Creates a new unmark command.
     * 
     * @param taskNumber the task number to unmark (1-based)
     */
    public UnmarkCommand(int taskNumber) {
        this.taskIndex = taskNumber - 1; // Convert to 0-based index
    }
    
    /**
     * Executes the unmark command by marking the specified task as not done.
     * 
     * @param taskList the task list containing the task to unmark
     * @param ui the UI component for showing confirmation
     * @param storage the storage component for saving changes
     * @return true to continue program execution
     * @throws LeBronException if the task number is invalid
     */
    @Override
    public boolean execute(TaskList taskList, Ui ui, FileManager storage) throws LeBronException {
        if (taskIndex < 0 || taskIndex >= taskList.size()) {
            throw new LeBronException(ErrorType.INVALID_TASK_NUMBER.getMessage());
        }
        
        taskList.unmarkTask(taskIndex);
        Task task = taskList.getTask(taskIndex);
        ui.showTaskUnmarked(task);
        
        try {
            storage.saveTasks(taskList.getTasks());
        } catch (Exception e) {
            ui.showSaveError();
        }
        
        return true;
    }
}
