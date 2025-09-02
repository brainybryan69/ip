package lebron;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import lebron.ui.Parser;
import lebron.common.CommandType;
import lebron.common.LeBronException;

public class ParserTest {

    @Test
    public void testParseCommand() {
        assertEquals(CommandType.TODO, Parser.parseCommand("todo read book"));
        assertEquals(CommandType.DEADLINE, Parser.parseCommand("deadline submit report /by tomorrow"));
        assertEquals(CommandType.EVENT, Parser.parseCommand("event meeting /from today /to tomorrow"));
        assertEquals(CommandType.LIST, Parser.parseCommand("list"));
        assertEquals(CommandType.MARK, Parser.parseCommand("mark 1"));
        assertEquals(CommandType.UNMARK, Parser.parseCommand("unmark 2"));
        assertEquals(CommandType.DELETE, Parser.parseCommand("delete 3"));
        assertEquals(CommandType.ON, Parser.parseCommand("on 2024-12-25"));
        assertEquals(CommandType.FIND, Parser.parseCommand("find book"));
        assertEquals(CommandType.FIND, Parser.parseCommand("find"));
        assertEquals(CommandType.BYE, Parser.parseCommand("bye"));
        assertEquals(CommandType.UNKNOWN, Parser.parseCommand("invalid command"));
    }

    @Test
    public void testParseTaskNumber() throws LeBronException {
        assertEquals(1, Parser.parseTaskNumber("mark 1", 4));
        assertEquals(5, Parser.parseTaskNumber("delete 5", 6));
        assertEquals(10, Parser.parseTaskNumber("unmark 10", 6));
    }

    @Test
    public void testParseTaskNumberInvalid() {
        // Invalid number
        assertThrows(LeBronException.class, () -> {
            Parser.parseTaskNumber("mark abc", 4);
        });

        // Zero or negative number
        assertThrows(LeBronException.class, () -> {
            Parser.parseTaskNumber("mark 0", 4);
        });

        assertThrows(LeBronException.class, () -> {
            Parser.parseTaskNumber("mark -1", 4);
        });

        // Missing number
        assertThrows(LeBronException.class, () -> {
            Parser.parseTaskNumber("mark", 4);
        });
    }

    @Test
    public void testParseTodoDescription() throws LeBronException {
        assertEquals("read book", Parser.parseTodoDescription("todo read book"));
        assertEquals("buy groceries and cook dinner",
                     Parser.parseTodoDescription("todo buy groceries and cook dinner"));
    }

    @Test
    public void testParseTodoDescriptionEmpty() {
        assertThrows(LeBronException.class, () -> {
            Parser.parseTodoDescription("todo");
        });

        assertThrows(LeBronException.class, () -> {
            Parser.parseTodoDescription("todo   ");
        });
    }

    @Test
    public void testParseDeadlineCommand() throws LeBronException {
        String[] result = Parser.parseDeadlineCommand("deadline submit report /by 2024-12-25 1800");
        assertEquals("submit report", result[0]);
        assertEquals("2024-12-25 1800", result[1]);

        String[] result2 = Parser.parseDeadlineCommand("deadline finish assignment /by tomorrow");
        assertEquals("finish assignment", result2[0]);
        assertEquals("tomorrow", result2[1]);
    }

    @Test
    public void testParseDeadlineCommandInvalid() {
        // Missing /by
        assertThrows(LeBronException.class, () -> {
            Parser.parseDeadlineCommand("deadline submit report tomorrow");
        });

        // Empty description
        assertThrows(LeBronException.class, () -> {
            Parser.parseDeadlineCommand("deadline /by tomorrow");
        });

        // Empty command
        assertThrows(LeBronException.class, () -> {
            Parser.parseDeadlineCommand("deadline");
        });
    }

    @Test
    public void testParseEventCommand() throws LeBronException {
        String[] result = Parser.parseEventCommand("event meeting /from 2024-12-25 1400 /to 2024-12-25 1600");
        assertEquals("meeting", result[0]);
        assertEquals("2024-12-25 1400", result[1]);
        assertEquals("2024-12-25 1600", result[2]);

        String[] result2 = Parser.parseEventCommand("event conference /from Monday 2pm /to Monday 5pm");
        assertEquals("conference", result2[0]);
        assertEquals("Monday 2pm", result2[1]);
        assertEquals("Monday 5pm", result2[2]);
    }

    @Test
    public void testParseEventCommandInvalid() {
        // Missing /from
        assertThrows(LeBronException.class, () -> {
            Parser.parseEventCommand("event meeting /to 2024-12-25 1600");
        });

        // Missing /to
        assertThrows(LeBronException.class, () -> {
            Parser.parseEventCommand("event meeting /from 2024-12-25 1400");
        });

        // Empty description
        assertThrows(LeBronException.class, () -> {
            Parser.parseEventCommand("event /from today /to tomorrow");
        });

        // Empty command
        assertThrows(LeBronException.class, () -> {
            Parser.parseEventCommand("event");
        });
    }

    @Test
    public void testParseOnCommand() throws LeBronException {
        assertEquals("2024-12-25", Parser.parseOnCommand("on 2024-12-25"));
        assertEquals("tomorrow", Parser.parseOnCommand("on tomorrow"));
    }

    @Test
    public void testParseOnCommandEmpty() {
        assertThrows(LeBronException.class, () -> {
            Parser.parseOnCommand("on");
        });

        assertThrows(LeBronException.class, () -> {
            Parser.parseOnCommand("on   ");
        });
    }

    @Test
    public void testParseFindCommand() throws LeBronException {
        assertEquals("book", Parser.parseFindCommand("find book"));
        assertEquals("homework assignment", Parser.parseFindCommand("find homework assignment"));
        assertEquals("BOOK", Parser.parseFindCommand("find BOOK"));
    }

    @Test
    public void testParseFindCommandEmpty() {
        assertThrows(LeBronException.class, () -> {
            Parser.parseFindCommand("find");
        });

        assertThrows(LeBronException.class, () -> {
            Parser.parseFindCommand("find   ");
        });
    }
}
