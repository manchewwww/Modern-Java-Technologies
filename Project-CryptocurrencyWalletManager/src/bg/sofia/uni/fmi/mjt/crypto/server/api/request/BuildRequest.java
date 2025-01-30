package bg.sofia.uni.fmi.mjt.crypto.server.api.request;

import java.net.URI;
import java.net.http.HttpRequest;

public class BuildRequest {

    private static final String X_COIN_API_KEY = "X-CoinAPI-Key";
    private static final String URL = "https://rest.coinapi.io/v1/assets";

    private final String apiKey;

    public BuildRequest(String apiKey) {
        this.apiKey = apiKey;
    }

    public HttpRequest getRequest() {
        return HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .header(X_COIN_API_KEY, apiKey)
            .build();
    }

}
