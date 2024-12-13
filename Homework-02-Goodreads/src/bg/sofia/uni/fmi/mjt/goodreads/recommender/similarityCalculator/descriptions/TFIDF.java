package bg.sofia.uni.fmi.mjt.goodreads.recommender.similarityCalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similarityCalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.*;

public class TFIDF implements SimilarityCalculator {

    public TFIDF(Set<Book> books, TextTokenizer tokenizer) {
    }

    @Override
    public double genresSimilarity(Book first, Book second) {
        return 0;
    }

    @Override
    public double descriptionSimilarity(Book first, Book second) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<String, Double> computeTFIDF(Book book) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<String, Double> computeTF(Book book) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<String, Double> computeIDF(Book book) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public double calculateSimilarity(Book first, Book second) {
        return 0;
    }

}
