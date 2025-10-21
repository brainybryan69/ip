package lebron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import lebron.common.LeBronException;
import lebron.task.Event;

public class EventTest {
    

    @Test
    public void testEventCreationWithStrings() throws LeBronException {
        Event event = new Event("Team meeting", "2024-12-25 1400", "2024-12-25 1600");
        assertEquals("Team meeting", event.getDescription());
        assertFalse(event.isDone());
        assertNotNull(event.getFrom());
        assertNotNull(event.getTo());
    }

    @Test
    public void testEventCreationWithLocalDateTime() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 25, 14, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 25, 16, 0);
        Event event = new Event("Conference", start, end);
        assertEquals("Conference", event.getDescription());
        assertEquals(start, event.getFrom());
        assertEquals(end, event.getTo());
    }

    @Test
    public void testGetFullDescription() throws LeBronException {
        Event event = new Event("Workshop", "2024-12-25 0900", "2024-12-25 1200");
        String fullDesc = event.getFullDescription();
        assertTrue(fullDesc.contains("Workshop"));
        assertTrue(fullDesc.contains("from:"));
        assertTrue(fullDesc.contains("to:"));
    }

    @Test
    public void testMarkAsDone() throws LeBronException {
        Event event = new Event("Meeting", "2024-12-25 1000", "2024-12-25 1100");
        event.markAsDone();
        assertTrue(event.isDone());
    }

    @Test
    public void testGetTypeIcon() throws LeBronException {
        Event event = new Event("Event", "2024-12-25 1000", "2024-12-25 1100");
        assertEquals("[E]", event.getTypeIcon());
    }

    @Test
    public void testInvalidDateThrowsException() {
        assertThrows(LeBronException.class, () -> {
            new Event("Invalid event", "invalid-start", "2024-12-25 1100");
        });

        assertThrows(LeBronException.class, () -> {
            new Event("Invalid event", "2024-12-25 1000", "invalid-end");
        });
    }
}
