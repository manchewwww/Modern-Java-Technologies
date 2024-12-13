package bg.sofia.uni.fmi.mjt.goodreads.recommender.similarityCalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similarityCalculator.SimilarityCalculator;

import java.util.Map;

public class CompositeSimilarityCalculator implements SimilarityCalculator {

    public CompositeSimilarityCalculator(Map<SimilarityCalculator, Double> similarityCalculatorMap) {

    }

    @Override
    public double genresSimilarity(Book first, Book second) {
        return 0;
    }

    @Override
    public double descriptionSimilarity(Book first, Book second) {
        return 0;
    }

    @Override
    public double calculateSimilarity(Book first, Book second) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
