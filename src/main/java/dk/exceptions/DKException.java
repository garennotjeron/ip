package dk.exceptions;

/**
 * Represents exceptions that are thrown due to
 * errors occurring during operations involving the DK Chatbot.
 */
public class DKException extends Exception{

    public DKException (String message) {
        super(message);
    }

    /**
     * Returns a String representation of the exception detected.
     * @return A String representation of the exception detected
     */
    @Override
    public String toString() {
        return "DK has detected this error: " + this.getMessage();
    }
}
