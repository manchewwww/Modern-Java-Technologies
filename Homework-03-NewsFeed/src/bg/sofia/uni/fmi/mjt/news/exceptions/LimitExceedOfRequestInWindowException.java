package bg.sofia.uni.fmi.mjt.news.exceptions;

public class LimitExceedOfRequestInWindowException extends ApiException {

    public LimitExceedOfRequestInWindowException(String message) {
        super(message);
    }

    public LimitExceedOfRequestInWindowException(String message, Throwable cause) {
        super(message, cause);
    }

}
