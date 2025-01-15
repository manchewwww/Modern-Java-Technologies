package bg.sofia.uni.fmi.mjt.news.request;

import java.net.URI;
import java.net.http.HttpRequest;

public class BuildRequest {

    private static final StringBuilder URL = new StringBuilder("https://newsapi.org/v2/top-headlines?");
    private static final String AUTHORIZATION = "Authorization";
    private final String apiKey;

    public BuildRequest(String apiKey) {
        this.apiKey = apiKey;
    }

    public HttpRequest buildRequest(String keyword, String category, String country, int page, int pageSize) {
        if (keyword == null) {
            throw new IllegalArgumentException("keyword cannot be null");
        }
        URL.append("q=").append(keyword);

        if (category != null && !category.isBlank()) {
            URL.append("&category=").append(category);
        }

        if (country != null && !country.isBlank()) {
            URL.append("&country=").append(country);
        }

        if (page != 0) {
            URL.append("&page=").append(page);
        }

        if (pageSize != 0) {
            URL.append("&pageSize=").append(pageSize);
        }

        return HttpRequest.newBuilder()
            .uri(URI.create(URL.toString()))
            .header(AUTHORIZATION, apiKey)
            .build();
    }

}
