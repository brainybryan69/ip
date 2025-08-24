package lebron;
import java.util.Scanner;

/**
 * Main orchestrator for the LeBron task manager application.
 * Coordinates all components and handles the main program loop.
 */
public class TaskManager {
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
     * @return the Command object representing the user's intent
     * @throws LeBronException if the command is invalid or malformed
     */
    private Command parseCommand(String input) throws LeBronException {
        CommandType commandType = Parser.parseCommand(input);
        
        switch (commandType) {
        case BYE:
            return new ExitCommand();
        case LIST:
            return new ListCommand();
        case MARK:
            int markTaskNumber = Parser.parseTaskNumber(input, 5);
            return new MarkCommand(markTaskNumber);
        case UNMARK:
            int unmarkTaskNumber = Parser.parseTaskNumber(input, 7);
            return new UnmarkCommand(unmarkTaskNumber);
        case DELETE:
            int deleteTaskNumber = Parser.parseTaskNumber(input, 7);
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
            return new FindCommand(dateString);
        case UNKNOWN:
        default:
            throw new LeBronException(ErrorType.UNKNOWN_COMMAND.getMessage());
        }
    }
}
