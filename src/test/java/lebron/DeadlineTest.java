package lebron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import lebron.common.LeBronException;
import lebron.task.Deadline;

public class DeadlineTest {

    @Test
    public void testDeadlineCreationWithString() throws LeBronException {
        Deadline deadline = new Deadline("Submit report", "2024-12-25 1800");
        assertEquals("Submit report", deadline.getDescription());
        assertFalse(deadline.isDone());
        assertNotNull(deadline.getBy());
    }

    @Test
    public void testDeadlineCreationWithLocalDateTime() {
        LocalDateTime dueDate = LocalDateTime.of(2024, 12, 25, 18, 0);
        Deadline deadline = new Deadline("Complete project", dueDate);
        assertEquals("Complete project", deadline.getDescription());
        assertEquals(dueDate, deadline.getBy());
    }

    @Test
    public void testGetFullDescription() throws LeBronException {
        Deadline deadline = new Deadline("Assignment", "2024-12-25 1800");
        String fullDesc = deadline.getFullDescription();
        assertTrue(fullDesc.contains("Assignment"));
        assertTrue(fullDesc.contains("by:"));
    }

    @Test
    public void testMarkAsDone() throws LeBronException {
        Deadline deadline = new Deadline("Study", "2024-12-25 1800");
        deadline.markAsDone();
        assertTrue(deadline.isDone());
    }

    @Test
    public void testGetTypeIcon() throws LeBronException {
        Deadline deadline = new Deadline("Task", "2024-12-25 1800");
        assertEquals("[D]", deadline.getTypeIcon());
    }

    @Test
    public void testInvalidDateThrowsException() {
        assertThrows(LeBronException.class, () -> {
            new Deadline("Invalid task", "invalid-date");
        });
    }
}
