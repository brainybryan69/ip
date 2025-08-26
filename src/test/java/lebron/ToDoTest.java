package lebron;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import lebron.task.ToDo;

public class ToDoTest {

    @Test
    public void testToDoCreation() {
        ToDo todo = new ToDo("Buy groceries");
        assertEquals("Buy groceries", todo.getDescription());
        assertFalse(todo.isDone());
    }

    @Test
    public void testMarkAsDone() {
        ToDo todo = new ToDo("Read book");
        todo.markAsDone();
        assertTrue(todo.isDone());
    }

    @Test
    public void testMarkAsNotDone() {
        ToDo todo = new ToDo("Exercise");
        todo.markAsDone();
        todo.markAsNotDone();
        assertFalse(todo.isDone());
    }

    @Test
    public void testGetFullDescription() {
        ToDo todo = new ToDo("Learn Java");
        assertEquals("Learn Java", todo.getFullDescription());
    }

    @Test
    public void testGetStatusIcon() {
        ToDo todo = new ToDo("Test task");
        assertEquals("[ ]", todo.getStatusIcon()); // Not done
        todo.markAsDone();
        assertEquals("[X]", todo.getStatusIcon()); // Done
    }

    @Test
    public void testGetTypeIcon() {
        ToDo todo = new ToDo("Any task");
        assertEquals("[T]", todo.getTypeIcon());
    }
}