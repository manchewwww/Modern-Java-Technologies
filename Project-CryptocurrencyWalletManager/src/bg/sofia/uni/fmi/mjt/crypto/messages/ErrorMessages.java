package bg.sofia.uni.fmi.mjt.crypto.messages;

public class ErrorMessages {

    public static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments";
    public static final String USER_EXISTS_MESSAGE = "User with username: %s already exists";
    public static final String USER_DOES_NOT_EXISTS_MESSAGE = "User with username: %s doesn't exists";
    public static final String CRYPTO_DOES_NOT_EXIST_MESSAGE = "Crypto with this asset id is not found";

    public static final String BAD_REQUEST_MESSAGE = "Bad Request -- There is something wrong with your request";
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized -- Your API key is wrong";
    public static final String FORBIDDEN_MESSAGE =
        "Forbidden -- Your API key doesn't have enough privileges to access this resource";
    public static final String TOO_MANY_REQUESTS_MESSAGE =
        "Too many requests -- You have exceeded your API key rate limits";
    public static final String NO_DATA_MESSAGE =
        "No data -- You requested specific single item that we don't have at this moment.";
    public static final String UNEXPECTED_RESPONSE_CODE_EXCEPTION_MESSAGE =
        "Unexpected response code from weather forecast service";

    public static final String REQUEST_INTERRUPTED_EXCEPTION_MESSAGE = "Request interrupted";
    public static final String IO_EXCEPTION_MESSAGE = "An I/O error occurred while sending the request";

}
