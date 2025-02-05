package bg.sofia.uni.fmi.mjt.crypto.exceptions;

public class NotLoginException extends Exception {

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(String message, Throwable cause) {
        super(message, cause);
    }

}
