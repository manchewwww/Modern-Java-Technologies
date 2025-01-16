package bg.sofia.uni.fmi.mjt.news.client;

import bg.sofia.uni.fmi.mjt.news.articles.Article;
import bg.sofia.uni.fmi.mjt.news.articles.Source;
import bg.sofia.uni.fmi.mjt.news.builder.Arguments;
import bg.sofia.uni.fmi.mjt.news.exceptions.ApiException;
import bg.sofia.uni.fmi.mjt.news.exceptions.BadRequestException;
import bg.sofia.uni.fmi.mjt.news.exceptions.InvalidApiKeyException;
import bg.sofia.uni.fmi.mjt.news.exceptions.LimitExceedOfRequestInWindowException;
import bg.sofia.uni.fmi.mjt.news.exceptions.ServerErrorException;
import bg.sofia.uni.fmi.mjt.news.response.ErrorResponse;
import bg.sofia.uni.fmi.mjt.news.response.OKResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewsClientTest {

    private static OKResponse okResponse;
    private static String okResponseToJson;

    private static Gson gson;

    private final HttpClient newsClientMock = mock();

    private final HttpResponse<String> httpNewsResponseMock = mock();

    private NewsClient newsClient;

    @BeforeAll
    public static void setUp() {
        gson = new Gson();
        Article article = new Article(new Source("bbc-news", "BBC News"), "author",
            "title", "description", "url", "2025-01-16T17:52:25.5196803Z", "content");
        Article article1 = new Article(new Source("bbc-news", "BBC News"), "author1",
            "title1", "description1", "url1", "2025-01-16T17:52:25.5196803Z", "content1");

        okResponse = new OKResponse("ok", 90, List.of(article, article1));
        okResponseToJson = gson.toJson(okResponse);
    }

    @BeforeEach
    public void before() throws IOException, InterruptedException {
        when(newsClientMock.send(Mockito.any(HttpRequest.class)
            , ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
            .thenReturn(httpNewsResponseMock);

        this.newsClient = new NewsClient(newsClientMock);
    }

    @Test
    public void testGetResponseWithNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> newsClient.getResponse(null),
            "When arguments are null program expect IllegalArgumentException");
    }

    @Test
    public void testGetResponseWithOnlyKeyword() throws ApiException {
        when(httpNewsResponseMock.statusCode()).thenReturn(200);
        when(httpNewsResponseMock.body()).thenReturn(okResponseToJson);

        Arguments arguments = Arguments.builder("trump")
            .build();

        OKResponse result = newsClient.getResponse(arguments);

        assertEquals(okResponse, result,
            "Incorrect response for request only with keyword");
    }

    @Test
    public void testGetResponseWithKeywordAndCountry() throws ApiException {
        when(httpNewsResponseMock.statusCode()).thenReturn(200);
        when(httpNewsResponseMock.body()).thenReturn(okResponseToJson);

        Arguments arguments = Arguments.builder("trump")
            .setCountry("BG")
            .build();

        OKResponse result = newsClient.getResponse(arguments);

        assertEquals(okResponse, result,
            "Incorrect response for request with keyword and country");
    }

    @Test
    public void testGetResponseWithKeywordAndCategory() throws ApiException {
        when(httpNewsResponseMock.statusCode()).thenReturn(200);
        when(httpNewsResponseMock.body()).thenReturn(okResponseToJson);

        Arguments arguments = Arguments.builder("trump")
            .setCategory("sport")
            .build();

        OKResponse result = newsClient.getResponse(arguments);

        assertEquals(okResponse, result,
            "Incorrect response for request with keyword and category");
    }

    @Test
    public void testGetResponseWithKeywordAndPage() throws ApiException {
        when(httpNewsResponseMock.statusCode()).thenReturn(200);
        when(httpNewsResponseMock.body()).thenReturn(okResponseToJson);

        Arguments arguments = Arguments.builder("trump")
            .setPage(1)
            .build();

        OKResponse result = newsClient.getResponse(arguments);

        assertEquals(okResponse, result,
            "Incorrect response for request with keyword and page");
    }

    @Test
    public void testGetResponseWithKeywordAndPageSize() throws ApiException {
        when(httpNewsResponseMock.statusCode()).thenReturn(200);
        when(httpNewsResponseMock.body()).thenReturn(okResponseToJson);

        Arguments arguments = Arguments.builder("trump")
            .setPageSize(1)
            .build();

        OKResponse result = newsClient.getResponse(arguments);

        assertEquals(okResponse, result,
            "Incorrect response for request with keyword and page");
    }

    @Test
    public void testGetResponseWithAll() throws ApiException {
        when(httpNewsResponseMock.statusCode()).thenReturn(200);
        when(httpNewsResponseMock.body()).thenReturn(okResponseToJson);

        Arguments arguments = Arguments.builder("trump")
            .setCategory("sport")
            .setCountry("us")
            .setPage(10)
            .setPageSize(1)
            .build();

        OKResponse result = newsClient.getResponse(arguments);

        assertEquals(okResponse, result,
            "Incorrect response for request with keyword and page");
    }

    @Test
    public void testGetResponseWithBadRequestStatusCode() {
        ErrorResponse badRequestResponse = new ErrorResponse("error", "badRequest",
            "badRequest");
        String badRequestResponseToJson = gson.toJson(badRequestResponse);

        when(httpNewsResponseMock.statusCode()).thenReturn(400);
        when(httpNewsResponseMock.body()).thenReturn(badRequestResponseToJson);

        Arguments arguments = Arguments.builder("trump")
            .build();

        assertThrows(BadRequestException.class, () -> newsClient.getResponse(arguments)
            , "When rest api return status code: 400 program expect BadRequestException");
    }

    @Test
    public void testGetResponseWithUnauthorizedStatusCode() {
        ErrorResponse unauthorized = new ErrorResponse("error", "incorrectApiKey",
            "Please, enter valid apiKey");
        String unauthorizedToJson = gson.toJson(unauthorized);

        when(httpNewsResponseMock.statusCode()).thenReturn(401);
        when(httpNewsResponseMock.body()).thenReturn(unauthorizedToJson);

        Arguments arguments = Arguments.builder("trump")
            .build();

        assertThrows(InvalidApiKeyException.class, () -> newsClient.getResponse(arguments)
            , "When rest api return status code: 401 program expect InvalidApiKeyException");
    }

    @Test
    public void testGetResponseWithTooManyRequestsStatusCode() {
        ErrorResponse tooManyRequests = new ErrorResponse("error", "tooManyRequests",
            "too many requests");
        String tooManyRequestsToJson = gson.toJson(tooManyRequests);

        when(httpNewsResponseMock.statusCode()).thenReturn(429);
        when(httpNewsResponseMock.body()).thenReturn(tooManyRequestsToJson);

        Arguments arguments = Arguments.builder("trump")
            .build();

        assertThrows(LimitExceedOfRequestInWindowException.class, () -> newsClient.getResponse(arguments)
            , "When rest api return status code: 429 program expect LimitExceedOfRequestInWindowException");
    }

    @Test
    public void testGetResponseWithServerErrorStatusCode() {
        ErrorResponse serverError = new ErrorResponse("error", "serverError",
            "serverError");
        String serverErrorToJson = gson.toJson(serverError);

        when(httpNewsResponseMock.statusCode()).thenReturn(500);
        when(httpNewsResponseMock.body()).thenReturn(serverErrorToJson);

        Arguments arguments = Arguments.builder("trump")
            .build();

        assertThrows(ServerErrorException.class, () -> newsClient.getResponse(arguments)
            , "When rest api return status code: 500 program expect ServerErrorException");
    }

    @Test
    public void testGetResponseWithUnexpectedStatusCode() {
        ErrorResponse unexpected = new ErrorResponse("error", "unexpected",
            "unexpected");
        String unexpectedToJson = gson.toJson(unexpected);

        when(httpNewsResponseMock.statusCode()).thenReturn(333);
        when(httpNewsResponseMock.body()).thenReturn(unexpectedToJson);

        Arguments arguments = Arguments.builder("trump")
            .build();

        assertThrows(ApiException.class, () -> newsClient.getResponse(arguments)
            , "When rest api return unexpected status code program expect ApiException");
    }

}

