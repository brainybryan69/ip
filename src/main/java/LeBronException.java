/**
 * Something went wrong with the user's input or a task operation.
 * This exception gets thrown when we need to show the user an error message.
 */
public class LeBronException extends Exception {
    
    /**
     * Creates a new exception with an error message.
     * 
     * @param message what went wrong (gets shown to the user)
     */
    public LeBronException(String message) {
        super(message);
    }
}