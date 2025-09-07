package lebron;

/**
 * A personal task manager that helps you keep track of your todos, deadlines, and events.
 * You can add tasks, mark them as done, delete them, and list everything you need to do.
 *
 * Just type commands like "todo read book" or "deadline submit report /by Friday"
 * and LeBron will help you stay organized!
 */
public class LeBron {
    private static final TaskManager SHARED_TASK_MANAGER = new TaskManager();

    /**
     * Starts the LeBron task manager.
     * Uses the shared TaskManager instance for consistency.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SHARED_TASK_MANAGER.run();
    }

    /**
     * Generates a response for the user's chat message using the shared TaskManager.
     * This allows GUI and console to share the same task data.
     * 
     * @param input the user's command input
     * @return the response from processing the command
     */
    public String getResponse(String input) {
        return SHARED_TASK_MANAGER.processCommand(input);
    }
}
