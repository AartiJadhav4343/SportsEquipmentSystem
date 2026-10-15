package exception;

public class IssueLimitExceededException extends Exception {
    public IssueLimitExceededException(String message) {
        super(message);
    }
}