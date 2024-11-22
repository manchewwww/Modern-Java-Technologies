package bg.sofia.uni.fmi.mjt.glovo.exception;

public class InvalidMaximumTimeForDeliveryException extends RuntimeException {

    public InvalidMaximumTimeForDeliveryException(String message) {
        super(message);
    }

    public InvalidMaximumTimeForDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }

}
