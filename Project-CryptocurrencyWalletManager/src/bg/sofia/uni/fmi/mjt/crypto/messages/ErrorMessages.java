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

    public static final String LOAD_USERS_ERROR = "Could not load user repository from file";

    public static final String WRITE_USERS_ERROR = "Could not save user repository to file";

    public static final String YOU_ARE_LOGIN_MESSAGE = "You are logged in profile!";
    public static final String YOU_ARE_NOT_LOGIN_MESSAGE = "You are not logged in profile!";

    public static final String INVALID_DEPOSIT_AMOUNT_MESSAGE = "Deposit amount must be greater than 0.0";
    public static final String INVALID_CURRENT_PRICE_MESSAGE = "Current price must be greater than zero.";
    public static final String CRYPTO_ASSETS_ID_NULL_MESSAGE = "Crypto asset id cannot be null.";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "You don't have enough money.";
    public static final String CURRENT_PRICES_NULL_MESSAGE = "currentPrices cannot be null";
    public static final String NO_INVESTMENTS_FOUND_MESSAGE = "No investments found.";

}
