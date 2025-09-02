package lebron;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import lebron.util.DateTimeParser;
import lebron.common.LeBronException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeParserTest {

    @Test
    public void testParseDateTime() throws LeBronException {
        LocalDateTime result = DateTimeParser.parseDateTime("2024-12-25 1830");
        assertEquals(2024, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
        assertEquals(18, result.getHour());
        assertEquals(30, result.getMinute());
    }

    @Test
    public void testParseDateTimeWithSpaces() throws LeBronException {
        LocalDateTime result = DateTimeParser.parseDateTime("  2024-12-25 1830  ");
        assertEquals(2024, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
    }

    @Test
    public void testParseDateTimeInvalidFormat() {
        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDateTime("2024/12/25 18:30");
        });

        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDateTime("25-12-2024 1830");
        });

        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDateTime("invalid");
        });
    }

    @Test
    public void testParseDateTimeEmpty() {
        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDateTime("");
        });

        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDateTime("   ");
        });

        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDateTime(null);
        });
    }

    @Test
    public void testFormatForDisplay() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 18, 30);
        String formatted = DateTimeParser.formatForDisplay(dateTime);
        assertEquals("Dec 25 2024 6:30pm", formatted);
    }

    @Test
    public void testFormatForStorage() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 18, 30);
        String formatted = DateTimeParser.formatForStorage(dateTime);
        assertTrue(formatted.contains("2024-12-25"));
        assertTrue(formatted.contains("18:30"));
    }

    @Test
    public void testParseFromStorage() throws LeBronException {
        LocalDateTime original = LocalDateTime.of(2024, 12, 25, 18, 30);
        String stored = DateTimeParser.formatForStorage(original);
        LocalDateTime parsed = DateTimeParser.parseFromStorage(stored);
        assertEquals(original, parsed);
    }

    @Test
    public void testParseFromStorageInvalid() {
        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseFromStorage("invalid format");
        });
    }

    @Test
    public void testParseDate() throws LeBronException {
        LocalDate result = DateTimeParser.parseDate("2024-12-25");
        assertEquals(2024, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
    }

    @Test
    public void testParseDateWithSpaces() throws LeBronException {
        LocalDate result = DateTimeParser.parseDate("  2024-12-25  ");
        assertEquals(2024, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
    }

    @Test
    public void testParseDateInvalidFormat() {
        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDate("2024/12/25");
        });

        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDate("25-12-2024");
        });

        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDate("invalid");
        });
    }

    @Test
    public void testParseDateEmpty() {
        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDate("");
        });

        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDate("   ");
        });

        assertThrows(LeBronException.class, () -> {
            DateTimeParser.parseDate(null);
        });
    }
}
