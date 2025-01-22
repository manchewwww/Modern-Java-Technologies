package bg.sofia.uni.fmi.mjt.crypto.exceptions;

public class CryptoNotFoundException extends RuntimeException {

    public CryptoNotFoundException(String message) {
        super(message);
    }

    public CryptoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
