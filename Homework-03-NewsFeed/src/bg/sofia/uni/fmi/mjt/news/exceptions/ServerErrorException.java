package bg.sofia.uni.fmi.mjt.news.exceptions;

public class ServerErrorException extends ApiException {

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

}
