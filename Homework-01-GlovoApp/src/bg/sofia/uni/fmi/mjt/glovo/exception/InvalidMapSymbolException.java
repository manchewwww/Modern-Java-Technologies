package bg.sofia.uni.fmi.mjt.glovo.exception;

public class InvalidMapSymbolException extends RuntimeException {

    public InvalidMapSymbolException(String message) {
        super(message);
    }

    public InvalidMapSymbolException(String message, Throwable cause) {
        super(message, cause);
    }

}
