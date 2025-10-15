package lebron.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Manages a collection of tasks with operations like add, delete, mark, and filter.
 * Encapsulates the task storage and provides clean methods for task management.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates a new empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list with existing tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        assert task != null : "Task cannot be null";
        tasks.add(task);
    }

    /**
     * Removes a task at the specified index.
     *
     * @param index the index of the task to remove (0-based)
     * @return the removed task
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Task deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task index must be within valid range");
        }
        return tasks.remove(index);
    }

    /**
     * Gets a task at the specified index.
     *
     * @param index the index of the task (0-based)
     * @return the task at the index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Task getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task index must be within valid range");
        }
        return tasks.get(index);
    }

    /**
     * Marks a task as done.
     *
     * @param index the index of the task to mark (0-based)
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public void markTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task index must be within valid range");
        }
        tasks.get(index).markAsDone();
    }

    /**
     * Marks a task as not done.
     *
     * @param index the index of the task to unmark (0-based)
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public void unmarkTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task index must be within valid range");
        }
        tasks.get(index).markAsNotDone();
    }

    /**
     * Gets the number of tasks in the list.
     *
     * @return the size of the task lis
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if there are no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Gets all tasks in the list.
     *
     * @return a copy of the task lis
     */
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Finds all tasks that occur on a specific date.
     * Includes deadlines due on that date and events that span that date.
     *
     * @param targetDate the date to search for
     * @return a new TaskList containing matching tasks
     */
    public TaskList getTasksOnDate(LocalDate targetDate) {
        ArrayList<Task> matchingTasks = tasks.stream()
            .filter(task -> {
                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    return deadline.getBy().toLocalDate().equals(targetDate);
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    LocalDate eventStartDate = event.getFrom().toLocalDate();
                    LocalDate eventEndDate = event.getTo().toLocalDate();
                    // Include event if target date falls within or on the event's date range
                    return !targetDate.isBefore(eventStartDate) && !targetDate.isAfter(eventEndDate);
                }
                return false;
            })
            .collect(Collectors.toCollection(ArrayList::new));

        return new TaskList(matchingTasks);
    }

    /**
     * Finds all tasks that contain the specified keyword in their description.
     * The search is case-insensitive and matches partial words.
     *
     * @param keyword the keyword to search for
     * @return a new TaskList containing matching tasks
     */
    public TaskList findTasksByKeyword(String keyword) {

        String lowerKeyword = keyword.toLowerCase();
        
        ArrayList<Task> matchingTasks = tasks.stream()
            .filter(task -> task.getDescription().toLowerCase().contains(lowerKeyword))
            .collect(Collectors.toCollection(ArrayList::new));

        return new TaskList(matchingTasks);
    }
}
