package bg.sofia.uni.fmi.mjt.crypto.api;

import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.ApiException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.BadRequestException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.ForbiddenException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.NoDataException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.TooManyRequestsException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.UnauthorizedException;
import bg.sofia.uni.fmi.mjt.crypto.server.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.wallet.Crypto;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoinApiTest {

    private static final Gson gson = new Gson();

    private final HttpClient coinClientMock = mock();
    private final HttpResponse<String> coinResponseMock = mock();

    private CoinApi coinApi;

    @BeforeEach
    public void setUp() throws IOException, InterruptedException, ApiException {
        when(coinClientMock.send(Mockito.any(HttpRequest.class)
            , ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
            .thenReturn(coinResponseMock);

        coinApi = new CoinApi(coinClientMock);
    }

    @Test
    public void testGetResponseWhenApiThrowsIOException() throws IOException, InterruptedException {
        when(coinClientMock.send(Mockito.any(HttpRequest.class)
            , ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenThrow(new IOException());

        assertThrows(ApiException.class, () -> coinApi.getResponse(),
            "When IOException was thrown by API, ApiException should be thrown");
    }

    @Test
    public void testGetResponseWhenApiThrowsInterruptedExceptionException() throws IOException, InterruptedException {
        when(coinClientMock.send(Mockito.any(HttpRequest.class)
            , ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenThrow(new InterruptedException());

        assertThrows(ApiException.class, () -> coinApi.getResponse(),
            "When InterruptedException was thrown by API, ApiException should be thrown");
    }

    @Test
    public void testGetResponseWithStatusCodeOK() throws ApiException {
        List<Crypto> cryptos = new ArrayList<>();
        cryptos.add(new Crypto("BTC", 100, 1, 0));
        cryptos.add(new Crypto("USD", 100, 0, 0));
        cryptos.add(new Crypto("ETH", 100, 1, 0));
        String cryptosToJSON = gson.toJson(cryptos);

        cryptos.remove(1);
        CacheData result = new CacheData(cryptos);

        when(coinResponseMock.statusCode()).thenReturn(200);
        when(coinResponseMock.body()).thenReturn(cryptosToJSON);

        CacheData response = coinApi.getResponse();

        assertEquals(result.getAvailableCryptos(), response.getAvailableCryptos(),
            "Incorrect response when status code is OK");
    }

    @Test
    public void testGetResponseWithStatusCodeBadRequest() {
        when(coinResponseMock.statusCode()).thenReturn(400);

        assertThrows(BadRequestException.class, () -> coinApi.getResponse(),
            "When status code is 400 BadRequestException should be thrown");
    }

    @Test
    public void testGetResponseWithStatusCodeUnauthorized() {
        when(coinResponseMock.statusCode()).thenReturn(401);

        assertThrows(UnauthorizedException.class, () -> coinApi.getResponse(),
            "When status code is 401 UnauthorizedException should be thrown");
    }

    @Test
    public void testGetResponseWithStatusCodeForbidden() {
        when(coinResponseMock.statusCode()).thenReturn(403);

        assertThrows(ForbiddenException.class, () -> coinApi.getResponse(),
            "When status code is 403 ForbiddenException should be thrown");
    }

    @Test
    public void testGetResponseWithStatusCodeTooManyRequests() {
        when(coinResponseMock.statusCode()).thenReturn(429);

        assertThrows(TooManyRequestsException.class, () -> coinApi.getResponse(),
            "When status code is 429 TooManyRequestsException should be thrown");
    }

    @Test
    public void testGetResponseWithStatusCodeNoData() {
        when(coinResponseMock.statusCode()).thenReturn(550);

        assertThrows(NoDataException.class, () -> coinApi.getResponse(),
            "When status code is 550 NoDataException should be thrown");
    }

    @Test
    public void testGetResponseWithUnexpectedStatusCode() {
        when(coinResponseMock.statusCode()).thenReturn(455);

        assertThrows(ApiException.class, () -> coinApi.getResponse(),
            "When status code is different from 200, 400, 401, 403, 429 and 550 " +
                "ApiException should be thrown");
    }

}

