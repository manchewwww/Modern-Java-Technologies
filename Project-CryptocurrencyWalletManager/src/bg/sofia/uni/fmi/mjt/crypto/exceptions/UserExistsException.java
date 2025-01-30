package bg.sofia.uni.fmi.mjt.crypto.exceptions;

public class UserExistsException extends Exception {

    public UserExistsException(String message) {
        super(message);
    }

    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
