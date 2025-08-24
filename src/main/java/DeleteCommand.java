/**
 * Command to delete a task from the task list.
 * Removes the specified task and saves changes to storage.
 */
public class DeleteCommand extends Command {
    private int taskIndex;
    
    /**
     * Creates a new delete command.
     * 
     * @param taskNumber the task number to delete (1-based)
     */
    public DeleteCommand(int taskNumber) {
        this.taskIndex = taskNumber - 1; // Convert to 0-based index
    }
    
    /**
     * Executes the delete command by removing the specified task.
     * 
     * @param taskList the task list to delete from
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
        
        Task deletedTask = taskList.deleteTask(taskIndex);
        ui.showTaskDeleted(deletedTask, taskList.size());
        
        try {
            storage.saveTasks(taskList.getTasks());
        } catch (Exception e) {
            ui.showSaveError();
        }
        
        return true;
    }
}