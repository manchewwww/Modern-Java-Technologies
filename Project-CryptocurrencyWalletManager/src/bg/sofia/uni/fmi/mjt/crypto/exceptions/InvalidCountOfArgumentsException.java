package bg.sofia.uni.fmi.mjt.crypto.exceptions;

public class InvalidCountOfArgumentsException extends Exception {

    public InvalidCountOfArgumentsException(String message) {
        super(message);
    }

    public InvalidCountOfArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }

}
