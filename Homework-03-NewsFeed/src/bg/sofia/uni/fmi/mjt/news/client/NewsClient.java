package bg.sofia.uni.fmi.mjt.news.client;

import bg.sofia.uni.fmi.mjt.news.builder.Arguments;
import bg.sofia.uni.fmi.mjt.news.exceptions.ApiException;
import bg.sofia.uni.fmi.mjt.news.factory.StatusCodeFactory;
import bg.sofia.uni.fmi.mjt.news.request.BuildRequest;
import bg.sofia.uni.fmi.mjt.news.response.OKResponse;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewsClient {

    private static final String REQUEST_INTERRUPTED_EXCEPTION_MESSAGE = "Request interrupted";
    private static final String IO_EXCEPTION_MESSAGE = "An I/O error occurred while sending the request";

    private final HttpClient client;
    private final String apiKey;

    public NewsClient(String apiKey) {
        this(HttpClient.newHttpClient(), apiKey);
    }

    public NewsClient(HttpClient client, String apiKey) {
        this.client = client;
        this.apiKey = apiKey;
    }

    public OKResponse getResponse(Arguments arguments)
        throws ApiException {
        HttpResponse<String> response;
        BuildRequest buildRequest = new BuildRequest(apiKey);
        HttpRequest request = buildRequest.buildRequest(arguments);

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new ApiException(REQUEST_INTERRUPTED_EXCEPTION_MESSAGE);
        } catch (IOException e) {
            throw new ApiException(IO_EXCEPTION_MESSAGE);
        }

        return StatusCodeFactory.parseElements(response);
    }

}
