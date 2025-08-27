package lebron.storage;

import lebron.task.*;
import lebron.common.LeBronException;
import lebron.util.DateTimeParser;
import java.io.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * Handles data persistence for the task manager.
 * Provides a clean interface for saving and loading tasks from storage.
 */
public class Storage {
    private FileManager fileManager;
    
    /**
     * Creates a new Storage instance using the default FileManager.
     */
    public Storage() {
        this.fileManager = new FileManager();
    }
    
    /**
     * Creates a new Storage instance with a custom FileManager.
     * 
     * @param fileManager the file manager to use for storage operations
     */
    public Storage(FileManager fileManager) {
        this.fileManager = fileManager;
    }
    
    /**
     * Loads tasks from storage.
     * 
     * @return ArrayList of tasks loaded from storage
     * @throws IOException if loading fails
     */
    public ArrayList<Task> load() throws IOException {
        return fileManager.loadTasks();
    }
    
    /**
     * Saves tasks to storage.
     * 
     * @param tasks the list of tasks to save
     * @throws IOException if saving fails
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        fileManager.saveTasks(tasks);
    }
    
    /**
     * Gets the underlying FileManager for backward compatibility.
     * 
     * @return the FileManager instance
     */
    public FileManager getFileManager() {
        return fileManager;
    }
}
