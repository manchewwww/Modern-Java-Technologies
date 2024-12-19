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
        } else if (maxN <= 0) {
            throw new IllegalArgumentException("maxN is negative");
        }

        Map<Book, Double> similarityScores = initialBooks.stream()
            .collect(Collectors.toMap(
                book -> book,
                book -> similarityCalculator.calculateSimilarity(origin, book)
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<Book, Double>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Map.Entry.comparingByKey(Comparator.comparing(Book::title))))
            .limit(maxN)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        SortedMap<Book, Double> sortedMap = new TreeMap<>((b1, b2) -> {
            int scoreComparison = similarityScores.get(b2).compareTo(similarityScores.get(b1));
            if (scoreComparison != 0) {
                return scoreComparison;
            }

            return b1.title().compareTo(b2.title());
        });

        sortedMap.putAll(similarityScores);
        return sortedMap;
    }

}
