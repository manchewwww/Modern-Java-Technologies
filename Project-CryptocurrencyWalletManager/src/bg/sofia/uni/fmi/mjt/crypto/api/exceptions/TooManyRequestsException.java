package bg.sofia.uni.fmi.mjt.crypto.api.exceptions;

public class TooManyRequestsException extends ApiException {

    public TooManyRequestsException(String message) {
        super(message);
    }

    public TooManyRequestsException(String message, Throwable cause) {
        super(message, cause);
    }

}
