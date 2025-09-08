package lebron;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import lebron.command.AddDeadlineCommand;
import lebron.command.AddEventCommand;
import lebron.command.AddTodoCommand;
import lebron.command.Command;
import lebron.command.DeleteCommand;
import lebron.command.ExitCommand;
import lebron.command.FindCommand;
import lebron.command.ListCommand;
import lebron.command.MarkCommand;
import lebron.command.OnCommand;
import lebron.command.UnmarkCommand;
import lebron.common.CommandType;
import lebron.common.ErrorType;
import lebron.common.LeBronException;
import lebron.storage.Storage;
import lebron.task.TaskList;
import lebron.ui.Parser;
import lebron.ui.Ui;

/**
 * Main orchestrator for the LeBron task manager application.
 * Coordinates all components and handles the main program loop.
 */
public class TaskManager {
    
    // Command length constants for parsing
    private static final int MARK_COMMAND_LENGTH = 5;
    private static final int UNMARK_COMMAND_LENGTH = 7;
    private static final int DELETE_COMMAND_LENGTH = 7;
    private TaskList taskList;
    private Ui ui;
    private Storage storage;
    private Scanner scanner;

    /**
     * Creates a new TaskManager with default components.
     */
    public TaskManager() {
        this.ui = new Ui();
        this.storage = new Storage();
        this.taskList = new TaskList();
        this.scanner = new Scanner(System.in);
        loadTasks(); // Load tasks for GUI usage
    }

    /**
     * Starts the task manager application.
     * Loads existing tasks, shows welcome message, and enters main loop.
     */
    public void run() {
        loadTasks();
        ui.showWelcome();

        boolean isRunning = true;
        while (isRunning) {
            try {
                String input = scanner.nextLine();

                if (input.trim().isEmpty()) {
                    ui.showError("");
                    continue;
                }

                Command command = parseCommand(input);
                isRunning = command.execute(taskList, ui, storage.getFileManager());

            } catch (LeBronException e) {
                ui.showError(e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Processes a single command and returns the response as a string.
     * Captures System.out during command execution to return as string.
     * Used for GUI integration.
     * 
     * @param input the user input command
     * @return the response message as a string
     */
    public String processCommand(String input) {
        // Capture System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream capturedOut = new PrintStream(baos);
        System.setOut(capturedOut);
        
        try {
            if (input.trim().isEmpty()) {
                ui.showError("");
                return baos.toString().trim();
            }
            
            Command command = parseCommand(input);
            command.execute(taskList, ui, storage.getFileManager());
            
            return baos.toString().trim();
            
        } catch (LeBronException e) {
            ui.showError(e.getMessage());
            return baos.toString().trim();
        } finally {
            // Always restore System.out
            System.setOut(originalOut);
        }
    }

    /**
     * Loads existing tasks from storage.
     * Shows error message if loading fails but continues execution.
     */
    private void loadTasks() {
        try {
            taskList = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadError();
        }
    }

    /**
     * Parses user input and creates the appropriate command object.
     *
     * @param input the user input string
     * @return the Command object representing the user's inten
     * @throws LeBronException if the command is invalid or malformed
     */
    private Command parseCommand(String input) throws LeBronException {
        CommandType commandType = Parser.parseCommand(input);

        switch (commandType) {
        case BYE:
            return new ExitCommand();
        case LIST:
            if (input.equals("list")) {
                return new ListCommand();
            } else {
                String dateString = Parser.parseListDateCommand(input);
                return new ListCommand(dateString);
            }
        case MARK:
            int markTaskNumber = Parser.parseTaskNumber(input, MARK_COMMAND_LENGTH);
            return new MarkCommand(markTaskNumber);
        case UNMARK:
            int unmarkTaskNumber = Parser.parseTaskNumber(input, UNMARK_COMMAND_LENGTH);
            return new UnmarkCommand(unmarkTaskNumber);
        case DELETE:
            int deleteTaskNumber = Parser.parseTaskNumber(input, DELETE_COMMAND_LENGTH);
            return new DeleteCommand(deleteTaskNumber);
        case TODO:
            String todoDescription = Parser.parseTodoDescription(input);
            return new AddTodoCommand(todoDescription);
        case DEADLINE:
            String[] deadlineData = Parser.parseDeadlineCommand(input);
            return new AddDeadlineCommand(deadlineData[0], deadlineData[1]);
        case EVENT:
            String[] eventData = Parser.parseEventCommand(input);
            return new AddEventCommand(eventData[0], eventData[1], eventData[2]);
        case ON:
            String dateString = Parser.parseOnCommand(input);
            return new OnCommand(dateString);
        case FIND:
            String keyword = Parser.parseFindCommand(input);
            return new FindCommand(keyword);
        case UNKNOWN:
        default:
            throw new LeBronException(ErrorType.UNKNOWN_COMMAND.getMessage());
        }
    }
}
