package bg.sofia.uni.fmi.mjt.crypto.api.exceptions;

public class NoDataException extends ApiException {

    public NoDataException(String message) {
        super(message);
    }

    public NoDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
