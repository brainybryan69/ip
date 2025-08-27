package lebron;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import lebron.command.FindCommand;
import lebron.task.TaskList;
import lebron.task.ToDo;
import lebron.task.Deadline;
import lebron.ui.Ui;
import lebron.storage.FileManager;
import lebron.common.LeBronException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FindCommandTest {
    private TaskList taskList;
    private Ui ui;
    private FileManager fileManager;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() throws LeBronException {
        taskList = new TaskList();
        ui = new Ui();
        fileManager = null; // Not needed for find command
        
        // Add some test tasks
        taskList.addTask(new ToDo("read book about programming"));
        taskList.addTask(new ToDo("buy groceries"));
        taskList.addTask(new Deadline("submit book report", "2024-12-25 1800"));
        
        // Capture output for testing
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testFindCommandWithMatches() {
        FindCommand findCommand = new FindCommand("book");
        boolean result = findCommand.execute(taskList, ui, fileManager);
        
        assertTrue(result); // Should return true to continue execution
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the matching tasks in your list:"));
        assertTrue(output.contains("read book about programming"));
        assertTrue(output.contains("submit book report"));
        assertFalse(output.contains("buy groceries"));
    }

    @Test
    public void testFindCommandWithNoMatches() {
        FindCommand findCommand = new FindCommand("missing");
        boolean result = findCommand.execute(taskList, ui, fileManager);
        
        assertTrue(result); // Should return true to continue execution
        String output = outputStream.toString();
        assertTrue(output.contains("No matching tasks found for keyword: missing"));
    }

    @Test
    public void testFindCommandCaseInsensitive() {
        FindCommand findCommand = new FindCommand("BOOK");
        boolean result = findCommand.execute(taskList, ui, fileManager);
        
        assertTrue(result);
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the matching tasks in your list:"));
        assertTrue(output.contains("read book about programming"));
        assertTrue(output.contains("submit book report"));
    }
}