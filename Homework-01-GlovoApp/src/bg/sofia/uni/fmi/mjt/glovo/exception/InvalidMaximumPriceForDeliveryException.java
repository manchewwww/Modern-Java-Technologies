package bg.sofia.uni.fmi.mjt.glovo.exception;

public class InvalidMaximumPriceForDeliveryException extends RuntimeException {

    public InvalidMaximumPriceForDeliveryException(String message) {
        super(message);
    }

    public InvalidMaximumPriceForDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }

}
