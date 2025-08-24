public class DKException extends Exception{

    public DKException (String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "DK has detected this error: " + this.getMessage();
    }
}
