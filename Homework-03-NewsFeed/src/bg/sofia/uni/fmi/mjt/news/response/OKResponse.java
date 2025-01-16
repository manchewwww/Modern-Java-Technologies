package bg.sofia.uni.fmi.mjt.news.response;

import bg.sofia.uni.fmi.mjt.news.articles.Article;

import java.util.List;

public record OKResponse(String status, int totalResults, List<Article> articles) {

}
