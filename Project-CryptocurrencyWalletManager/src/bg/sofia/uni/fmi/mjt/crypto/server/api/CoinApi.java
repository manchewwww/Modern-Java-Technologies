package bg.sofia.uni.fmi.mjt.crypto.server.api;

import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.api.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.server.api.exceptions.ApiException;
import bg.sofia.uni.fmi.mjt.crypto.server.api.factory.StatusCodeFactory;
import bg.sofia.uni.fmi.mjt.crypto.server.api.request.BuildRequest;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CoinApi {

    private static final String API_KEY = "YOUR_API_KEY";

    private static final Gson GSON = new Gson();

    private final HttpClient httpClient;
    private final String apiKey;

    public CoinApi(HttpClient httpClient) {
        this(httpClient, API_KEY);
    }

    public CoinApi(HttpClient httpClient, String apiKey) {
        this.httpClient = httpClient;
        this.apiKey = apiKey;
    }

    public CacheData getResponse() throws ApiException {
        HttpResponse<String> response;
        BuildRequest buildRequest = new BuildRequest(apiKey);
        HttpRequest httpRequest = buildRequest.getRequest();

        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new ApiException(ErrorMessages.IO_EXCEPTION_MESSAGE);
        } catch (InterruptedException e) {
            throw new ApiException(ErrorMessages.REQUEST_INTERRUPTED_EXCEPTION_MESSAGE);
        }

        return StatusCodeFactory.parseElements(response);
    }

}
