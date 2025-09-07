package lebron.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import lebron.common.LeBronException;
import lebron.task.Deadline;
import lebron.task.Event;
import lebron.task.Task;
import lebron.task.ToDo;
import lebron.util.DateTimeParser;

/**
 * Handles saving and loading tasks to/from the hard disk.
 * Keeps your tasks safe by automatically storing them in a file.
 */
public class FileManager {
    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "lebron.txt";
    private final String filePath;

    /**
     * Creates a new file manager.
     * Sets up the path to store tasks using OS-independent file separators.
     */
    public FileManager() {
        this.filePath = DATA_DIR + File.separator + FILE_NAME;
    }

    /**
     * Saves all tasks to the hard disk.
     * Creates the data folder if it doesn't exist.
     *
     * @param tasks the list of tasks to save
     * @throws IOException if something goes wrong with file writing
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        assert tasks != null : "Task list cannot be null";
        // Create data directory if it doesn't exis
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.println(taskToString(task));
            }
        }
    }

    /**
     * Loads all tasks from the hard disk.
     * Returns an empty list if the file doesn't exist.
     * Skips corrupted lines and continues loading valid tasks.
     *
     * @return the list of saved tasks
     * @throws IOException if something goes wrong with file reading
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // Return empty list if file doesn't exis
        if (!file.exists()) {
            return tasks;
        }

        int lineNumber = 0;
        int corruptedLines = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                Task task = stringToTask(line, lineNumber);
                if (task != null) {
                    tasks.add(task);
                } else {
                    corruptedLines++;
                }
            }
        }

        // Inform user if some data was corrupted
        if (corruptedLines > 0) {
            logWarning("%d corrupted task(s) were skipped while loading.", corruptedLines);
        }

        return tasks;
    }

    /**
     * Converts a task to a string format for saving.
     * Format: TYPE|STATUS|DESCRIPTION|EXTRA_INFO
     *
     * @param task the task to conver
     * @return the string representation
     */
    private String taskToString(Task task) {
        StringBuilder sb = new StringBuilder();

        // Add type
        if (task instanceof ToDo) {
            sb.append("T");
        } else if (task instanceof Deadline) {
            sb.append("D");
        } else if (task instanceof Event) {
            sb.append("E");
        }

        sb.append("|");

        // Add status
        sb.append(task.isDone() ? "1" : "0");
        sb.append("|");

        // Add description
        sb.append(task.getDescription());

        // Add extra info for deadlines and events
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            sb.append("|").append(DateTimeParser.formatForStorage(deadline.getBy()));
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append("|").append(DateTimeParser.formatForStorage(event.getFrom())).append("|").append(DateTimeParser.formatForStorage(event.getTo()));
        }

        return sb.toString();
    }

    /**
     * Converts a string back to a task.
     * Parses the saved format and creates the right type of task.
     * Provides detailed validation and error reporting for corrupted data.
     *
     * @param line the string representation of the task
     * @param lineNumber the line number in the file (for error reporting)
     * @return the recreated task, or null if format is invalid
     */
    private Task stringToTask(String line, int lineNumber) {
        if (line.isEmpty()) {
            return null;
        }

        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            logWarning("Line %d has too few fields - expected at least 3, got %d", lineNumber, parts.length);
            return null;
        }
        assert parts.length >= 3 : "Parser validation should have caught this";

        String type = parts[0];
        String statusStr = parts[1];
        String description = parts[2];

        // Validate task type
        if (!isValidTaskType(type)) {
            logWarning("Line %d has invalid task type '%s' - expected T, D, or E", lineNumber, type);
            return null;
        }

        // Validate status
        if (!isValidStatus(statusStr)) {
            logWarning("Line %d has invalid status '%s' - expected 0 or 1", lineNumber, statusStr);
            return null;
        }

        // Validate description
        if (description.trim().isEmpty()) {
            logWarning("Line %d has empty description", lineNumber);
            return null;
        }

        boolean isDone = statusStr.equals("1");
        Task task = null;

        try {
            switch (type) {
            case "T":
                if (parts.length != 3) {
                    logWarning("Line %d - Todo tasks should have exactly 3 fields, got %d", lineNumber, parts.length);
                    return null;
                }
                task = new ToDo(description);
                break;
            case "D":
                if (parts.length != 4) {
                    logWarning("Line %d - Deadline tasks should have exactly 4 fields, got %d", lineNumber, parts.length);
                    return null;
                }
                String by = parts[3];
                if (by.trim().isEmpty()) {
                    logWarning("Line %d - Deadline has empty 'by' field", lineNumber);
                    return null;
                }
                try {
                    LocalDateTime dateTime = DateTimeParser.parseFromStorage(by);
                    assert dateTime != null : "DateTimeParser should not return null for valid input";
                    task = new Deadline(description, dateTime);
                } catch (LeBronException e) {
                    logWarning("Line %d - Invalid date format in deadline: %s", lineNumber, e.getMessage());
                    return null;
                }
                break;
            case "E":
                if (parts.length != 5) {
                    logWarning("Line %d - Event tasks should have exactly 5 fields, got %d", lineNumber, parts.length);
                    return null;
                }
                String from = parts[3];
                String to = parts[4];
                if (from.trim().isEmpty() || to.trim().isEmpty()) {
                    logWarning("Line %d - Event has empty time fields", lineNumber);
                    return null;
                }
                try {
                    LocalDateTime fromDateTime = DateTimeParser.parseFromStorage(from);
                    LocalDateTime toDateTime = DateTimeParser.parseFromStorage(to);
                    assert fromDateTime != null && toDateTime != null : "DateTimeParser should not return null for valid input";
                    task = new Event(description, fromDateTime, toDateTime);
                } catch (LeBronException e) {
                    logWarning("Line %d - Invalid date format in event: %s", lineNumber, e.getMessage());
                    return null;
                }
                break;
            }

            // Set the completion status
            if (task != null && isDone) {
                task.markAsDone();
            }

        } catch (Exception e) {
            logWarning("Line %d - Error creating task: %s", lineNumber, e.getMessage());
            return null;
        }

        return task;
    }

    /**
     * Checks if the task type character is valid.
     *
     * @param type the type character to validate
     * @return true if valid (T, D, or E), false otherwise
     */
    private boolean isValidTaskType(String type) {
        return type.equals("T") || type.equals("D") || type.equals("E");
    }

    /**
     * Checks if the status string is valid.
     *
     * @param status the status string to validate
     * @return true if valid (0 or 1), false otherwise
     */
    private boolean isValidStatus(String status) {
        return status.equals("0") || status.equals("1");
    }

    /**
     * Logs a warning message with formatted output using varargs.
     * 
     * @param format the format string
     * @param args the arguments for the format string
     */
    private void logWarning(String format, Object... args) {
        System.out.printf("Warning: " + format + "%n", args);
    }

}
