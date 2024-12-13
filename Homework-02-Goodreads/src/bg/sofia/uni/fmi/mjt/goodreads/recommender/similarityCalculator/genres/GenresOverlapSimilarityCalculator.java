package bg.sofia.uni.fmi.mjt.goodreads.recommender.similarityCalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similarityCalculator.SimilarityCalculator;

public class GenresOverlapSimilarityCalculator implements SimilarityCalculator {

    @Override
    public double genresSimilarity(Book first, Book second) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public double descriptionSimilarity(Book first, Book second) {
        return 0;
    }

    @Override
    public double calculateSimilarity(Book first, Book second) {
        return 0;
    }

}
