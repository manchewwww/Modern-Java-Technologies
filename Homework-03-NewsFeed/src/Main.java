import bg.sofia.uni.fmi.mjt.news.client.NewsClient;
import bg.sofia.uni.fmi.mjt.news.exceptions.ApiException;
import bg.sofia.uni.fmi.mjt.news.response.OKResponse;

public class Main {

    static String apiKEy = "02c74a646ebb4f2f91f754bdd664ba40";

    public static void main(String[] args) {
        try {
            NewsClient newsClient = new NewsClient(apiKEy);
            //OK WITH ONLY KEYWORDS
//        OKResponse okResponse = newsClient.getResponse("trump", null, null, 0, 0);
//        System.out.println(okResponse.getStatus());
//        System.out.println(okResponse.getTotalResults());
//        System.out.println(okResponse.getArticles());

            OKResponse okResponse = newsClient.getResponse("trump", null, "US", 2, 2);
            System.out.println(okResponse.getStatus());
            System.out.println(okResponse.getTotalResults());
            System.out.println(okResponse.getArticles());

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
