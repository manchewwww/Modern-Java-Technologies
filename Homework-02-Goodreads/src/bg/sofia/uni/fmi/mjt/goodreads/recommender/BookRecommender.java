package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BookRecommender implements BookRecommenderAPI {

    private final Set<Book> initialBooks;
    private final SimilarityCalculator similarityCalculator;

    public BookRecommender(Set<Book> initialBooks, SimilarityCalculator calculator) {
        this.initialBooks = initialBooks;
        this.similarityCalculator = calculator;
    }

    @Override
    public SortedMap<Book, Double> recommendBooks(Book origin, int maxN) {
        if (origin == null) {
            throw new IllegalArgumentException("origin is null");
        }
        if (maxN <= 0) {
            throw new IllegalArgumentException("maxN is negative");
        }

        return initialBooks.stream()
            .collect(Collectors.toMap(
                book -> book,
                book -> similarityCalculator.calculateSimilarity(origin, book)
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
            .limit(maxN)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                () -> new TreeMap<>(
                    Comparator.comparingDouble((Book b) -> similarityCalculator.calculateSimilarity(origin, b))
                        .reversed()))
            );
    }

}
