package bg.sofia.uni.fmi.mjt.news.articles;

public record Article(Source source, String author, String title, String description, String url, String publishedAt,
                      String content) {

}
