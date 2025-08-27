package lebron.command;

import lebron.task.*;
import lebron.ui.Ui;
import lebron.storage.FileManager;
import lebron.common.*;
import lebron.util.DateTimeParser;
import java.time.LocalDate;

/**
 * Command to find and display tasks containing a keyword in their description.
 * Shows all tasks whose description contains the specified keyword.
 */
public class FindCommand extends Command {
    private String keyword;
    
    /**
     * Creates a new find command for the specified keyword.
     * 
     * @param keyword the keyword to search for in task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * Executes the find command by searching for tasks containing the keyword.
     * 
     * @param taskList the task list to search in
     * @param ui the UI component for displaying results
     * @param storage the storage component (not used for searching)
     * @return true to continue program execution
     */
    @Override
    public boolean execute(TaskList taskList, Ui ui, FileManager storage) {
        TaskList matchingTasks = taskList.findTasksByKeyword(keyword);
        ui.showFindResults(matchingTasks, keyword);
        return true;
    }
}
