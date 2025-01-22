package bg.sofia.uni.fmi.mjt.crypto.exceptions;

public class InvalidAmountOfDepositException extends Exception {

    public InvalidAmountOfDepositException(String message) {
        super(message);
    }

    public InvalidAmountOfDepositException(String message, Throwable cause) {
        super(message, cause);
    }

}
