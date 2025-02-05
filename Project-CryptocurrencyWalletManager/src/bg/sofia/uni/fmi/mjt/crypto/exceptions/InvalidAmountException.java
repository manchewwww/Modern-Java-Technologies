package bg.sofia.uni.fmi.mjt.crypto.exceptions;

public class InvalidAmountException extends Exception {

    public InvalidAmountException(String message) {
        super(message);
    }

    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }

}
