/**
 * Abstract base class for all commands in the LeBron task manager.
 * Defines the common interface that all command types must implement.
 */
public abstract class Command {
    
    /**
     * Executes the command with the given task list, UI, and storage components.
     * Each concrete command implements its specific logic in this method.
     * 
     * @param taskList the task list to operate on
     * @param ui the UI component for user interaction
     * @param storage the storage component for saving/loading
     * @return true if the program should continue running, false to exit
     * @throws LeBronException if there's an error executing the command
     */
    public abstract boolean execute(TaskList taskList, Ui ui, FileManager storage) throws LeBronException;
    
    /**
     * Checks if this command will cause the program to exit.
     * 
     * @return true if this is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}