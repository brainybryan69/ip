package lebron;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import lebron.task.TaskList;
import lebron.task.Task;
import lebron.task.ToDo;
import lebron.task.Deadline;
import lebron.task.Event;
import lebron.common.LeBronException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskListTest {
    private TaskList taskList;
    private ToDo todo;
    private Deadline deadline;
    private Event event;

    @BeforeEach
    public void setUp() throws LeBronException {
        taskList = new TaskList();
        todo = new ToDo("Buy groceries");
        deadline = new Deadline("Submit report", "2024-12-25 1800");
        event = new Event("Team meeting", "2024-12-25 1400", "2024-12-25 1600");
    }

    @Test
    public void testEmptyTaskListCreation() {
        assertTrue(taskList.isEmpty());
        assertEquals(0, taskList.size());
    }

    @Test
    public void testAddTask() {
        taskList.addTask(todo);
        assertEquals(1, taskList.size());
        assertFalse(taskList.isEmpty());
        assertEquals(todo, taskList.getTask(0));
    }

    @Test
    public void testDeleteTask() {
        taskList.addTask(todo);
        taskList.addTask(deadline);
        
        Task deletedTask = taskList.deleteTask(0);
        assertEquals(todo, deletedTask);
        assertEquals(1, taskList.size());
        assertEquals(deadline, taskList.getTask(0));
    }

    @Test
    public void testDeleteTaskInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.deleteTask(0);
        });
    }

    @Test
    public void testGetTask() {
        taskList.addTask(todo);
        taskList.addTask(deadline);
        
        assertEquals(todo, taskList.getTask(0));
        assertEquals(deadline, taskList.getTask(1));
    }

    @Test
    public void testGetTaskInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.getTask(0);
        });
    }

    @Test
    public void testMarkTask() {
        taskList.addTask(todo);
        assertFalse(todo.isDone());
        
        taskList.markTask(0);
        assertTrue(todo.isDone());
    }

    @Test
    public void testUnmarkTask() {
        taskList.addTask(todo);
        todo.markAsDone();
        assertTrue(todo.isDone());
        
        taskList.unmarkTask(0);
        assertFalse(todo.isDone());
    }

    @Test
    public void testMarkTaskInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.markTask(0);
        });
    }

    @Test
    public void testGetTasks() {
        taskList.addTask(todo);
        taskList.addTask(deadline);
        
        var tasks = taskList.getTasks();
        assertEquals(2, tasks.size());
        assertEquals(todo, tasks.get(0));
        assertEquals(deadline, tasks.get(1));
        
        // Ensure it returns a copy, not the original list
        tasks.clear();
        assertEquals(2, taskList.size());
    }

    @Test
    public void testGetTasksOnDate() throws LeBronException {
        LocalDate targetDate = LocalDate.of(2024, 12, 25);
        
        // Add tasks for the target date
        taskList.addTask(todo); // ToDo - shouldn't match
        taskList.addTask(deadline); // Deadline on 2024-12-25
        taskList.addTask(event); // Event on 2024-12-25
        
        // Add a deadline for a different date
        Deadline otherDeadline = new Deadline("Other task", "2024-12-26 1000");
        taskList.addTask(otherDeadline);
        
        TaskList matchingTasks = taskList.getTasksOnDate(targetDate);
        assertEquals(2, matchingTasks.size()); // Should match deadline and event
        
        // Verify the matching tasks are correct
        var tasks = matchingTasks.getTasks();
        assertTrue(tasks.contains(deadline));
        assertTrue(tasks.contains(event));
        assertFalse(tasks.contains(todo));
        assertFalse(tasks.contains(otherDeadline));
    }

    @Test
    public void testGetTasksOnDateMultiDayEvent() throws LeBronException {
        // Create a multi-day event
        Event multiDayEvent = new Event("Conference", "2024-12-25 0900", "2024-12-27 1700");
        taskList.addTask(multiDayEvent);
        
        // Test dates within the event period
        TaskList day1 = taskList.getTasksOnDate(LocalDate.of(2024, 12, 25));
        TaskList day2 = taskList.getTasksOnDate(LocalDate.of(2024, 12, 26));
        TaskList day3 = taskList.getTasksOnDate(LocalDate.of(2024, 12, 27));
        
        assertEquals(1, day1.size());
        assertEquals(1, day2.size());
        assertEquals(1, day3.size());
        
        // Test date outside the event period
        TaskList outsideEvent = taskList.getTasksOnDate(LocalDate.of(2024, 12, 28));
        assertEquals(0, outsideEvent.size());
    }

    @Test
    public void testFindTasksByKeyword() throws LeBronException {
        // Add tasks with different descriptions
        ToDo bookTodo = new ToDo("read book about programming");
        ToDo bookTodo2 = new ToDo("buy BOOK from store");
        ToDo homeworkTodo = new ToDo("finish homework assignment");
        Deadline bookDeadline = new Deadline("submit book report", "2024-12-25 1800");
        
        taskList.addTask(bookTodo);
        taskList.addTask(bookTodo2);
        taskList.addTask(homeworkTodo);
        taskList.addTask(bookDeadline);
        
        // Test case-insensitive search
        TaskList bookResults = taskList.findTasksByKeyword("book");
        assertEquals(3, bookResults.size());
        assertTrue(bookResults.getTasks().contains(bookTodo));
        assertTrue(bookResults.getTasks().contains(bookTodo2));
        assertTrue(bookResults.getTasks().contains(bookDeadline));
        assertFalse(bookResults.getTasks().contains(homeworkTodo));
        
        // Test uppercase search
        TaskList bookResultsUpper = taskList.findTasksByKeyword("BOOK");
        assertEquals(3, bookResultsUpper.size());
        
        // Test partial match
        TaskList homeworkResults = taskList.findTasksByKeyword("homework");
        assertEquals(1, homeworkResults.size());
        assertTrue(homeworkResults.getTasks().contains(homeworkTodo));
        
        // Test no matches
        TaskList noResults = taskList.findTasksByKeyword("missing");
        assertEquals(0, noResults.size());
        assertTrue(noResults.isEmpty());
    }

    @Test
    public void testFindTasksByKeywordEmptyList() {
        TaskList emptyList = new TaskList();
        TaskList results = emptyList.findTasksByKeyword("anything");
        assertEquals(0, results.size());
        assertTrue(results.isEmpty());
    }
}