package bg.sofia.uni.fmi.mjt.crypto.api;

import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.ApiException;
import bg.sofia.uni.fmi.mjt.crypto.api.factory.StatusCodeFactory;
import bg.sofia.uni.fmi.mjt.crypto.api.request.BuildRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Properties;

public class CoinApi {

    private static final String API_KEY = "API_KEY";

    private final HttpClient httpClient;
    private final String apiKey;

    public CoinApi(HttpClient httpClient) throws ApiException {
        this.httpClient = httpClient;
        this.apiKey = loadApiKey();
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

    private String loadApiKey() throws ApiException {
        Path path =
            Path.of("src", "bg", "sofia", "uni", "fmi", "mjt", "crypto",
                "api", "key", "config.properties");

        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream(path.toFile())) {
            properties.load(inputStream);
            return properties.getProperty(API_KEY);
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
    }

}
