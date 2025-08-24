import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Handles parsing and formatting of dates and times for LeBron.
 * Understands various input formats and converts them to a standard display format.
 */
public class DateTimeParser {
    
    // Input format: yyyy-mm-dd HHmm (e.g., 2019-12-02 1800)
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    
    // Display format: MMM dd yyyy h:mma (e.g., Dec 02 2019 6:00PM)
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
    
    // Storage format: ISO format for file storage
    private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    /**
     * Parses a date/time string into a LocalDateTime object.
     * Accepts format: yyyy-mm-dd HHmm (e.g., "2019-12-02 1800")
     * 
     * @param dateTimeString the date/time string to parse
     * @return the parsed LocalDateTime
     * @throws LeBronException if the format is invalid
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws LeBronException {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            throw new LeBronException("Date and time cannot be empty");
        }
        
        try {
            return LocalDateTime.parse(dateTimeString.trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new LeBronException("Invalid date format. Use yyyy-mm-dd HHmm (e.g., 2019-12-02 1800)");
        }
    }
    
    /**
     * Formats a LocalDateTime for display to the user.
     * Output format: MMM dd yyyy h:mma (e.g., "Dec 02 2019 6:00PM")
     * 
     * @param dateTime the LocalDateTime to format
     * @return the formatted date/time string
     */
    public static String formatForDisplay(LocalDateTime dateTime) {
        return dateTime.format(DISPLAY_FORMAT);
    }
    
    /**
     * Formats a LocalDateTime for storage in files.
     * Uses ISO format for consistency and parseability.
     * 
     * @param dateTime the LocalDateTime to format
     * @return the formatted date/time string for storage
     */
    public static String formatForStorage(LocalDateTime dateTime) {
        return dateTime.format(STORAGE_FORMAT);
    }
    
    /**
     * Parses a stored date/time string back into LocalDateTime.
     * Used when loading from file storage.
     * 
     * @param storedDateTime the stored date/time string
     * @return the parsed LocalDateTime
     * @throws LeBronException if parsing fails
     */
    public static LocalDateTime parseFromStorage(String storedDateTime) throws LeBronException {
        try {
            return LocalDateTime.parse(storedDateTime, STORAGE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new LeBronException("Corrupted date/time data in storage");
        }
    }
}