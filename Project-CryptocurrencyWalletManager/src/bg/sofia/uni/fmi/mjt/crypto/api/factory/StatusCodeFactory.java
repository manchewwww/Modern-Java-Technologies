package bg.sofia.uni.fmi.mjt.crypto.api.factory;

import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.ApiException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.BadRequestException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.ForbiddenException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.NoDataException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.TooManyRequestsException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.UnauthorizedException;
import bg.sofia.uni.fmi.mjt.crypto.wallet.Crypto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.List;

public class StatusCodeFactory {

    private static final int OK = 200;
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int TOO_MANY_REQUESTS = 429;
    private static final int NO_DATA = 550;
    private static final Gson GSON = new Gson();
    
    public static CacheData parseElements(HttpResponse<String> response) throws ApiException {
        if (response.statusCode() == OK) {
            Type type = new TypeToken<List<Crypto>>() {
            }.getType();
            List<Crypto> cryptos = GSON.fromJson(response.body(), type);
            return new CacheData(cryptos);
        } else if (response.statusCode() == BAD_REQUEST) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST_MESSAGE);
        } else if (response.statusCode() == UNAUTHORIZED) {
            throw new UnauthorizedException(ErrorMessages.UNAUTHORIZED_MESSAGE);
        } else if (response.statusCode() == FORBIDDEN) {
            throw new ForbiddenException(ErrorMessages.FORBIDDEN_MESSAGE);
        } else if (response.statusCode() == TOO_MANY_REQUESTS) {
            throw new TooManyRequestsException(ErrorMessages.TOO_MANY_REQUESTS_MESSAGE);
        } else if (response.statusCode() == NO_DATA) {
            throw new NoDataException(ErrorMessages.NO_DATA_MESSAGE);
        } else {
            throw new ApiException(ErrorMessages.UNEXPECTED_RESPONSE_CODE_EXCEPTION_MESSAGE);
        }
    }

}
