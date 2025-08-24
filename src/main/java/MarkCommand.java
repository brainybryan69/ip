/**
 * Command to mark a task as completed.
 * Updates the task's status and saves changes to storage.
 */
public class MarkCommand extends Command {
    private int taskIndex;
    
    /**
     * Creates a new mark command.
     * 
     * @param taskNumber the task number to mark (1-based)
     */
    public MarkCommand(int taskNumber) {
        this.taskIndex = taskNumber - 1; // Convert to 0-based index
    }
    
    /**
     * Executes the mark command by marking the specified task as done.
     * 
     * @param taskList the task list containing the task to mark
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
        
        taskList.markTask(taskIndex);
        Task task = taskList.getTask(taskIndex);
        ui.showTaskMarked(task);
        
        try {
            storage.saveTasks(taskList.getTasks());
        } catch (Exception e) {
            ui.showSaveError();
        }
        
        return true;
    }
}