package bg.sofia.uni.fmi.mjt.crypto.api.exceptions;

public class ForbiddenException extends ApiException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

}
