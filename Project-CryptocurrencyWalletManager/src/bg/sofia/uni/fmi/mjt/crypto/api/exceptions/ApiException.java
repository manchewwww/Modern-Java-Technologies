package bg.sofia.uni.fmi.mjt.crypto.api.exceptions;

public class ApiException extends Exception {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
