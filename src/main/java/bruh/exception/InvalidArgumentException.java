package bruh.exception;

/**
 * Represents an exception thrown whenever an unrecognized argument is provided to Bruh.
 */
public class InvalidArgumentException extends BruhException {
    private static final String ERROR_MESSAGE = "Sorry, but I'm not sure what that means :(";

    /**
     * Constructor for an exception thrown whenever an unrecognized command is provided to Bruh.
     */
    public InvalidArgumentException() {
        super(ERROR_MESSAGE);
    }
}
