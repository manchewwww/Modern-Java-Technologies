package bg.sofia.uni.fmi.mjt.news.response;

public class ErrorResponse {

    private final String error;
    private final String code;
    private final String message;

    public  ErrorResponse(String error, String code, String message) {
        this.error = error;
        this.code = code;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
