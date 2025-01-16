package bg.sofia.uni.fmi.mjt.news.request;

import bg.sofia.uni.fmi.mjt.news.builder.Arguments;

import java.net.URI;
import java.net.http.HttpRequest;

public class BuildRequest {

    private static final String KEYWORD_MISSING_EXCEPTION_MESSAGE = "Keyword cannot be null";
    private static final String KEYWORD_TEXT_FOR_REQUEST = "q=";
    private static final String CATEGORY_TEXT_FOR_REQUEST = "&category=";
    private static final String COUNTRY_TEXT_FOR_REQUEST = "&country=";
    private static final String PAGE_TEXT_FOR_REQUEST = "&page=";
    private static final String PAGE_SIZE_TEXT_FOR_REQUEST = "&pageSize=";
    private static final String AUTHORIZATION = "Authorization";

    private final StringBuilder url = new StringBuilder("https://newsapi.org/v2/top-headlines?");
    private final String apiKey;

    public BuildRequest(String apiKey) {
        this.apiKey = apiKey;
    }

    public HttpRequest buildRequest(Arguments arguments) {
        url.append(KEYWORD_TEXT_FOR_REQUEST).append(arguments.getKeyword());

        if (arguments.getCategory() != null) {
            url.append(CATEGORY_TEXT_FOR_REQUEST).append(arguments.getCategory());
        }

        if (arguments.getCountry() != null) {
            url.append(COUNTRY_TEXT_FOR_REQUEST).append(arguments.getCountry());
        }

        if (arguments.getPage() != 0) {
            url.append(PAGE_TEXT_FOR_REQUEST).append(arguments.getPage());
        }

        if (arguments.getPageSize() != 0) {
            url.append(PAGE_SIZE_TEXT_FOR_REQUEST).append(arguments.getPageSize());
        }

        return HttpRequest.newBuilder()
            .uri(URI.create(url.toString()))
            .header(AUTHORIZATION, apiKey)
            .build();
    }

}
