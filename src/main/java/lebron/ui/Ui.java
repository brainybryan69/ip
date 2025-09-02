package lebron.ui;

import java.time.LocalDate;

import lebron.task.Task;
import lebron.task.TaskList;
import lebron.util.DateTimeParser;

/**
 * Handles all user interface interactions including input/output formatting.
 * Centralizes all user-facing messages and display logic.
 */
public class Ui {
    private static final String NAME = "LeBron";

    /**
     * Shows the welcome message when the program starts.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm " + NAME);
        System.out.println("What can I do for you?");
    }

    /**
     * Shows the goodbye message when the program exits.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Shows an error message to the user.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        if (!message.isEmpty()) {
            System.out.println(message);
        }
    }

    /**
     * Shows all tasks in the task list.
     *
     * @param taskList the task list to display
     */
    public void showTaskList(TaskList taskList) {
        if (taskList.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.getTask(i);
                System.out.println((i + 1) + "." + task.getTypeIcon() + task.getStatusIcon() + " " + task.getFullDescription());
            }
        }
    }

    /**
     * Shows confirmation message when a task is added.
     *
     * @param task the task that was added
     * @param taskCount the total number of tasks after adding
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task.getTypeIcon() + task.getStatusIcon() + " " + task.getFullDescription());
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Shows confirmation message when a task is marked as done.
     *
     * @param task the task that was marked
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.getStatusIcon() + " " + task.getDescription());
    }

    /**
     * Shows confirmation message when a task is unmarked.
     *
     * @param task the task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task.getStatusIcon() + " " + task.getDescription());
    }

    /**
     * Shows confirmation message when a task is deleted.
     *
     * @param task the task that was deleted
     * @param remainingCount the number of tasks remaining after deletion
     */
    public void showTaskDeleted(Task task, int remainingCount) {
        System.out.println("Noted. I've removed the task:");
        System.out.println(task.getTypeIcon() + task.getStatusIcon() + " " + task.getFullDescription());
        System.out.println("Now you have " + remainingCount + " tasks in the list.");
    }

    /**
     * Shows tasks that occur on a specific date.
     *
     * @param taskList the filtered task list containing matching tasks
     * @param targetDate the date that was searched for
     */
    public void showTasksOnDate(TaskList taskList, LocalDate targetDate) {
        String dateStr = DateTimeParser.formatForDisplay(targetDate.atStartOfDay()).substring(0, 11);

        if (taskList.isEmpty()) {
            System.out.println("No tasks found on " + dateStr + ".");
        } else {
            System.out.println("Tasks on " + dateStr + ":");
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.getTask(i);
                System.out.println((i + 1) + "." + task.getTypeIcon() + task.getStatusIcon() + " " + task.getFullDescription());
            }
        }
    }

    /**
     * Shows a message when tasks cannot be loaded from file.
     */
    public void showLoadError() {
        System.out.println("Could not load saved tasks.");
    }

    /**
     * Shows a message when tasks cannot be saved to file.
     */
    public void showSaveError() {
        System.out.println("Could not save tasks to file.");
    }

    /**
     * Shows the results of a find command with the matching tasks.
     *
     * @param taskList the list of tasks that match the search keyword
     * @param keyword the keyword that was searched for
     */
    public void showFindResults(TaskList taskList, String keyword) {
        if (taskList.isEmpty()) {
            System.out.println("No matching tasks found for keyword: " + keyword);
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.getTask(i);
                System.out.println((i + 1) + "." + task.getTypeIcon() + task.getStatusIcon() + " " + task.getFullDescription());
            }
        }
    }
}
