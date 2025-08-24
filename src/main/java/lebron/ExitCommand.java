package lebron;
/**
 * Command to exit the LeBron program.
 * Shows goodbye message and signals the program to terminate.
 */
public class ExitCommand extends Command {
    
    /**
     * Executes the exit command by showing goodbye message.
     * 
     * @param taskList the task list (not used for exit)
     * @param ui the UI component for displaying goodbye message
     * @param storage the storage component (not used for exit)
     * @return false to indicate the program should exit
     */
    @Override
    public boolean execute(TaskList taskList, Ui ui, FileManager storage) {
        ui.showGoodbye();
        return false;
    }
    
    /**
     * Indicates this command will cause program exit.
     * 
     * @return true since this is an exit command
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
