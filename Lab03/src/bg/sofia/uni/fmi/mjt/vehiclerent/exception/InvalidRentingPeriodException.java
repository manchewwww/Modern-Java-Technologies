package bg.sofia.uni.fmi.mjt.vehiclerent.exception;

public class InvalidRentingPeriodException extends Exception {
    public InvalidRentingPeriodException(String message) {
        super(message);
    }

    public InvalidRentingPeriodException(String message, Throwable cause) {
        super(message, cause);
    }
}