package bg.sofia.uni.fmi.mjt.news.response;

import bg.sofia.uni.fmi.mjt.news.articles.Article;

import java.util.List;

public class OKResponse {
    private String status;
    private int totalResults;
    private List<Article> articles;

    public OKResponse(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

}
