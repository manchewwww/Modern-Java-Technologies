package bg.sofia.uni.fmi.mjt.news.request;

import bg.sofia.uni.fmi.mjt.news.builder.Arguments;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildRequestTest {

    @Test
    public void testCreating() {
        Arguments arguments = Arguments.builder(Set.of("trump"))
            .setPageSize(1)
            .setPage(1)
            .setCategory("sport")
            .setCountry("us")
            .build();
        BuildRequest request = new BuildRequest("MY_API_KEY");

        String result = "https://newsapi.org/v2/top-headlines?q=trump&category=sport&country=us&page=1&pageSize=1";
        assertEquals(result, request.buildRequest(arguments).uri().toString(),
            "BuildRequest return incorrect uri");
    }

}
