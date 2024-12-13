package bg.sofia.uni.fmi.mjt.goodreads.recommender.similarityCalculator;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;

public interface SimilarityCalculator {

    double genresSimilarity(Book first, Book second);

    double descriptionSimilarity(Book first, Book second);

    /**
     * Calculates the similarity between two books.
     *
     * @param first, second - Books used for similarity calculation
     * @throws IllegalArgumentException if first or second is null
     * @return a double - score of similarity
     */
    double calculateSimilarity(Book first, Book second);

}