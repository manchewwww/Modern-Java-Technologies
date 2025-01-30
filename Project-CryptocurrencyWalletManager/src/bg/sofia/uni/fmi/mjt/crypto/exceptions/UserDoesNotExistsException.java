package bg.sofia.uni.fmi.mjt.crypto.exceptions;

public class UserDoesNotExistsException extends Exception {

    public UserDoesNotExistsException(String message) {
        super(message);
    }

    public UserDoesNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
