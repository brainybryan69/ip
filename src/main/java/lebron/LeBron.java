package lebron;

/**
 * A personal task manager that helps you keep track of your todos, deadlines, and events.
 * You can add tasks, mark them as done, delete them, and list everything you need to do.
 *
 * Just type commands like "todo read book" or "deadline submit report /by Friday"
 * and LeBron will help you stay organized!
 */
public class LeBron {

    /**
     * Starts the LeBron task manager.
     * Creates a TaskManager instance and runs the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new TaskManager().run();
    }
}
