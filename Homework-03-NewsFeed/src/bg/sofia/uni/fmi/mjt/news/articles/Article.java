package bg.sofia.uni.fmi.mjt.news.articles;

public class Article {

    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String publishedAt;
    private String content;

    public Article(Source source, String author, String title, String description, String url, String publishedAt,
                   String content) {
        this.source = source;
        this.title = title;
        this.author = author;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
