package bg.sofia.uni.fmi.mjt.crypto.api.exceptions;

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
