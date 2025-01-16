package bg.sofia.uni.fmi.mjt.news.factory;

import bg.sofia.uni.fmi.mjt.news.exceptions.ApiException;
import bg.sofia.uni.fmi.mjt.news.exceptions.BadRequestException;
import bg.sofia.uni.fmi.mjt.news.exceptions.InvalidApiKeyException;
import bg.sofia.uni.fmi.mjt.news.exceptions.LimitExceedOfRequestInWindowException;
import bg.sofia.uni.fmi.mjt.news.exceptions.ServerErrorException;
import bg.sofia.uni.fmi.mjt.news.response.ErrorResponse;
import bg.sofia.uni.fmi.mjt.news.response.OKResponse;
import com.google.gson.Gson;

import java.net.http.HttpResponse;

public class StatusCodeFactory {

    private static final String UNEXPECTED_RESPONSE_CODE_EXCEPTION_MESSAGE =
        "Unexpected response code from weather forecast service";
    private static final int OK = 200;
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int TOO_MANY_REQUESTS = 429;
    private static final int SERVER_ERROR = 500;
    private static final Gson GSON = new Gson();

    public static OKResponse parseElements(HttpResponse<String> response) throws ApiException {
        if (response.statusCode() == OK) {
            return GSON.fromJson(response.body(), OKResponse.class);
        } else if (response.statusCode() == BAD_REQUEST) {
            ErrorResponse errorResponse = GSON.fromJson(response.body(), ErrorResponse.class);
            throw new BadRequestException(errorResponse.message());
        } else if (response.statusCode() == UNAUTHORIZED) {
            ErrorResponse errorResponse = GSON.fromJson(response.body(), ErrorResponse.class);
            throw new InvalidApiKeyException(errorResponse.message());
        } else if (response.statusCode() == TOO_MANY_REQUESTS) {
            ErrorResponse errorResponse = GSON.fromJson(response.body(), ErrorResponse.class);
            throw new LimitExceedOfRequestInWindowException(errorResponse.message());
        } else if (response.statusCode() == SERVER_ERROR) {
            ErrorResponse errorResponse = GSON.fromJson(response.body(), ErrorResponse.class);
            throw new ServerErrorException(errorResponse.message());
        } else {
            throw new ApiException(UNEXPECTED_RESPONSE_CODE_EXCEPTION_MESSAGE);
        }
    }

}
