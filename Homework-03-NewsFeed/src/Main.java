import bg.sofia.uni.fmi.mjt.news.builder.Arguments;
import bg.sofia.uni.fmi.mjt.news.client.NewsClient;
import bg.sofia.uni.fmi.mjt.news.exceptions.ApiException;
import bg.sofia.uni.fmi.mjt.news.request.BuildRequest;
import bg.sofia.uni.fmi.mjt.news.response.OKResponse;

import java.net.http.HttpClient;

public class Main {

    static String apiKEy = "MY_API_KEY";

    public static void main(String[] args) {
        try {
            NewsClient newsClient = new NewsClient(HttpClient.newHttpClient(), apiKEy);

            BuildRequest request = new BuildRequest("dasdas");

            //OK WITH ONLY KEYWORDS
            Arguments arguments1 = Arguments.builder("trump")
                .build();
            OKResponse okResponse1 = newsClient.getResponse(arguments1);
            System.out.println(okResponse1.status());
            System.out.println(okResponse1.totalResults());
            System.out.println(okResponse1.articles().size());

            Arguments arguments2 = Arguments.builder("trump")
                .setCountry("US")
                .build();
            OKResponse okResponse2 = newsClient.getResponse(arguments2);
            System.out.println(okResponse2.status());
            System.out.println(okResponse2.totalResults());
            System.out.println(okResponse2.articles().size());

            //ERROR BECAUSE KEYWORD IS NULL
//            OKResponse okResponse = newsClient.getResponse(null, null, null, 0, 0);
//            System.out.println(okResponse.getStatus());
//            System.out.println(okResponse.getTotalResults());
//            System.out.println(okResponse.getArticles());
        } catch (ApiException e) {
            System.out.println("Api: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Ill: " + e.getMessage());
        }
    }

}
