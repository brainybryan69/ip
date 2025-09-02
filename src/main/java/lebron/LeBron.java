package lebron;

/**
 * A personal task manager that helps you keep track of your todos, deadlines, and events.
 * You can add tasks, mark them as done, delete them, and list everything you need to do.
 *
 * Just type commands like "todo read book" or "deadline submit report /by Friday"
 * and LeBron will help you stay organized!
 */
public class LeBron {
    private static TaskManager sharedTaskManager = new TaskManager();

    /**
     * Starts the LeBron task manager.
     * Creates a TaskManager instance and runs the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new TaskManager().run();
    }

    /**
     * Generates a response for the user's chat message using the shared TaskManager.
     * This allows GUI and console to share the same task data.
     * 
     * @param input the user's command input
     * @return the response from processing the command
     */
    public String getResponse(String input) {
        return sharedTaskManager.processCommand(input);
    }
}
