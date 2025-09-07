package lebron.ui;

import lebron.common.CommandType;
import lebron.common.ErrorType;
import lebron.common.LeBronException;
/**
 * Handles parsing of user input commands and extracting relevant information.
 * Converts raw user input into structured data for command execution.
 */
public class Parser {

    /**
     * Parses user input to determine the command type.
     *
     * @param input the raw user inpu
     * @return the CommandType representing the user's inten
     */
    public static CommandType parseCommand(String input) {
        return CommandType.parseCommand(input);
    }

    /**
     * Extracts task number from mark/unmark/delete commands.
     *
     * @param input the user input (e.g., "mark 1", "delete 3")
     * @param commandLength the length of the command word
     * @return the task number (1-based)
     * @throws LeBronException if the task number is invalid
     */
    public static int parseTaskNumber(String input, int commandLength) throws LeBronException {
        assert input != null : "Input string cannot be null";
        assert commandLength >= 0 : "Command length must be non-negative";
        try {
            String numberStr = input.substring(commandLength).trim();
            int taskNumber = Integer.parseInt(numberStr);
            if (taskNumber <= 0) {
                throw new LeBronException(ErrorType.INVALID_TASK_NUMBER.getMessage());
            }
            return taskNumber;
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new LeBronException(ErrorType.INVALID_TASK_NUMBER.getMessage());
        }
    }

    /**
     * Extracts the description from a todo command.
     *
     * @param input the user input (e.g., "todo read book")
     * @return the task description
     * @throws LeBronException if the description is empty
     */
    public static String parseTodoDescription(String input) throws LeBronException {
        assert input != null : "Input string cannot be null";
        String description = input.length() > 4 ? input.substring(5).trim() : "";
        if (description.isEmpty()) {
            throw new LeBronException(ErrorType.EMPTY_TODO.getMessage());
        }
        return description;
    }

    /**
     * Extracts description and deadline from a deadline command.
     *
     * @param input the user input (e.g., "deadline submit report /by 2025-12-02 1800")
     * @return array containing [description, deadline]
     * @throws LeBronException if format is invalid or components are missing
     */
    public static String[] parseDeadlineCommand(String input) throws LeBronException {
        return parseDeadlineCommand(input, ErrorType.EMPTY_DEADLINE.getMessage(),
                                  ErrorType.MISSING_DEADLINE_FORMAT.getMessage());
    }

    /**
     * Extracts description and deadline from a deadline command with custom error messages.
     *
     * @param input the user input
     * @param errorMessages custom error messages for empty deadline and missing format
     * @return array containing [description, deadline]
     * @throws LeBronException if format is invalid or components are missing
     */
    public static String[] parseDeadlineCommand(String input, String... errorMessages) throws LeBronException {
        assert input != null : "Input string cannot be null";
        String emptyDeadlineMsg = errorMessages.length > 0 ? errorMessages[0] : ErrorType.EMPTY_DEADLINE.getMessage();
        String missingFormatMsg = errorMessages.length > 1 ? errorMessages[1] 
                : ErrorType.MISSING_DEADLINE_FORMAT.getMessage();
        String remaining = input.length() > 8 ? input.substring(9) : "";
        if (remaining.trim().isEmpty()) {
            throw new LeBronException(emptyDeadlineMsg);
        }

        int byIndex = remaining.indexOf(" /by ");
        if (byIndex == -1) {
            throw new LeBronException(missingFormatMsg);
        }

        String description = remaining.substring(0, byIndex).trim();
        if (description.isEmpty()) {
            throw new LeBronException(emptyDeadlineMsg);
        }

        String by = remaining.substring(byIndex + 5);
        return new String[]{description, by};
    }

    /**
     * Extracts description, start time, and end time from an event command.
     *
     * @param input the user input (e.g., "event meeting /from 2025-12-02 1400 /to 2025-12-02 1600")
     * @return array containing [description, from, to]
     * @throws LeBronException if format is invalid or components are missing
     */
    public static String[] parseEventCommand(String input) throws LeBronException {
        return parseEventCommand(input, ErrorType.EMPTY_EVENT.getMessage(),
                               ErrorType.MISSING_EVENT_FORMAT.getMessage());
    }

    /**
     * Extracts description, start time, and end time from an event command with custom error messages.
     *
     * @param input the user input
     * @param errorMessages custom error messages for empty event and missing format
     * @return array containing [description, from, to]
     * @throws LeBronException if format is invalid or components are missing
     */
    public static String[] parseEventCommand(String input, String... errorMessages) throws LeBronException {
        assert input != null : "Input string cannot be null";
        String emptyEventMsg = errorMessages.length > 0 ? errorMessages[0] : ErrorType.EMPTY_EVENT.getMessage();
        String missingFormatMsg = errorMessages.length > 1 ? errorMessages[1] 
                : ErrorType.MISSING_EVENT_FORMAT.getMessage();
        String remaining = input.length() > 5 ? input.substring(6) : "";
        if (remaining.trim().isEmpty()) {
            throw new LeBronException(emptyEventMsg);
        }

        int fromIndex = remaining.indexOf(" /from ");
        int toIndex = remaining.indexOf(" /to ");

        if (fromIndex == -1 || toIndex == -1) {
            throw new LeBronException(missingFormatMsg);
        }

        String description = remaining.substring(0, fromIndex).trim();
        if (description.isEmpty()) {
            throw new LeBronException(emptyEventMsg);
        }

        String from = remaining.substring(fromIndex + 7, toIndex);
        String to = remaining.substring(toIndex + 5);

        return new String[]{description, from, to};
    }

    /**
     * Extracts date from an "on" command.
     *
     * @param input the user input (e.g., "on 2025-12-02")
     * @return the date string
     * @throws LeBronException if the date is missing
     */
    public static String parseOnCommand(String input) throws LeBronException {
        assert input != null : "Input string cannot be null";
        String dateString = input.length() > 2 ? input.substring(3).trim() : "";
        if (dateString.isEmpty()) {
            throw new LeBronException("Please specify a date. Use: on yyyy-mm-dd (e.g., on 2019-12-02)");
        }
        return dateString;
    }

    /**
     * Extracts keyword from a "find" command.
     *
     * @param input the user input (e.g., "find book")
     * @return the keyword to search for
     * @throws LeBronException if the keyword is missing
     */
    public static String parseFindCommand(String input) throws LeBronException {
        assert input != null : "Input string cannot be null";
        String keyword = input.length() > 4 ? input.substring(5).trim() : "";
        if (keyword.isEmpty()) {
            throw new LeBronException("Please specify a keyword to search for. Use: find <keyword> (e.g., find book)");
        }
        return keyword;
    }
}
