import java.io.*;
import java.util.ArrayList;

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
        // Create data directory if it doesn't exist
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
        
        // Return empty list if file doesn't exist
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
            System.out.println("Warning: " + corruptedLines + " corrupted task(s) were skipped while loading.");
        }
        
        return tasks;
    }
    
    /**
     * Converts a task to a string format for saving.
     * Format: TYPE|STATUS|DESCRIPTION|EXTRA_INFO
     * 
     * @param task the task to convert
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
            sb.append("|").append(getDeadlineBy(task));
        } else if (task instanceof Event) {
            sb.append("|").append(getEventFrom(task)).append("|").append(getEventTo(task));
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
            System.out.println("Warning: Line " + lineNumber + " has too few fields - expected at least 3, got " + parts.length);
            return null;
        }
        
        String type = parts[0];
        String statusStr = parts[1];
        String description = parts[2];
        
        // Validate task type
        if (!isValidTaskType(type)) {
            System.out.println("Warning: Line " + lineNumber + " has invalid task type '" + type + "' - expected T, D, or E");
            return null;
        }
        
        // Validate status
        if (!isValidStatus(statusStr)) {
            System.out.println("Warning: Line " + lineNumber + " has invalid status '" + statusStr + "' - expected 0 or 1");
            return null;
        }
        
        // Validate description
        if (description.trim().isEmpty()) {
            System.out.println("Warning: Line " + lineNumber + " has empty description");
            return null;
        }
        
        boolean isDone = statusStr.equals("1");
        Task task = null;
        
        try {
            switch (type) {
            case "T":
                if (parts.length != 3) {
                    System.out.println("Warning: Line " + lineNumber + " - Todo tasks should have exactly 3 fields, got " + parts.length);
                    return null;
                }
                task = new ToDo(description);
                break;
            case "D":
                if (parts.length != 4) {
                    System.out.println("Warning: Line " + lineNumber + " - Deadline tasks should have exactly 4 fields, got " + parts.length);
                    return null;
                }
                String by = parts[3];
                if (by.trim().isEmpty()) {
                    System.out.println("Warning: Line " + lineNumber + " - Deadline has empty 'by' field");
                    return null;
                }
                task = new Deadline(description, by);
                break;
            case "E":
                if (parts.length != 5) {
                    System.out.println("Warning: Line " + lineNumber + " - Event tasks should have exactly 5 fields, got " + parts.length);
                    return null;
                }
                String from = parts[3];
                String to = parts[4];
                if (from.trim().isEmpty() || to.trim().isEmpty()) {
                    System.out.println("Warning: Line " + lineNumber + " - Event has empty time fields");
                    return null;
                }
                task = new Event(description, from, to);
                break;
            }
            
            // Set the completion status
            if (task != null && isDone) {
                task.markAsDone();
            }
            
        } catch (Exception e) {
            System.out.println("Warning: Line " + lineNumber + " - Error creating task: " + e.getMessage());
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
     * Gets the deadline date from a deadline task.
     * Uses reflection to access private field safely.
     * 
     * @param task the deadline task
     * @return the deadline date
     */
    private String getDeadlineBy(Task task) {
        // Since we know this is a Deadline, we can cast it
        // For now, we'll use the string representation to extract the date
        String fullDesc = task.getFullDescription();
        int byStart = fullDesc.indexOf("(by: ") + 5;
        int byEnd = fullDesc.lastIndexOf(")");
        return fullDesc.substring(byStart, byEnd);
    }
    
    /**
     * Gets the start time from an event task.
     * 
     * @param task the event task
     * @return the start time
     */
    private String getEventFrom(Task task) {
        String fullDesc = task.getFullDescription();
        int fromStart = fullDesc.indexOf("(from: ") + 7;
        int fromEnd = fullDesc.indexOf(" to: ");
        return fullDesc.substring(fromStart, fromEnd);
    }
    
    /**
     * Gets the end time from an event task.
     * 
     * @param task the event task
     * @return the end time
     */
    private String getEventTo(Task task) {
        String fullDesc = task.getFullDescription();
        int toStart = fullDesc.indexOf(" to: ") + 5;
        int toEnd = fullDesc.lastIndexOf(")");
        return fullDesc.substring(toStart, toEnd);
    }
}