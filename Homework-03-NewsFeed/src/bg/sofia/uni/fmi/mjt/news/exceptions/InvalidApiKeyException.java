package bg.sofia.uni.fmi.mjt.news.exceptions;

public class InvalidApiKeyException extends ApiException {

    public InvalidApiKeyException(String message) {
        super(message);
    }

    public InvalidApiKeyException(String message, Throwable cause) {
        super(message, cause);
    }

}
