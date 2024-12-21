package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TFIDFSimilarityCalculator implements SimilarityCalculator {

    private final Set<Book> books;
    private final TextTokenizer tokenizer;

    public TFIDFSimilarityCalculator(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    /*
     * Do not modify!
     */
    @Override
    public double calculateSimilarity(Book first, Book second) {
        Map<String, Double> tfIdfScoresFirst = computeTFIDF(first);
        Map<String, Double> tfIdfScoresSecond = computeTFIDF(second);

        return cosineSimilarity(tfIdfScoresFirst, tfIdfScoresSecond);
    }

    public Map<String, Double> computeTFIDF(Book book) {
        Map<String, Double> tf = computeTF(book);
        Map<String, Double> idf = computeIDF(book);

        return idf.keySet().stream()
            .collect(Collectors.toMap(word -> word, word -> idf.get(word) * tf.get(word)));
    }

    public Map<String, Double> computeTF(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        Map<String, Double> tf = new HashMap<>();
        List<String> words = tokenizer.tokenize(book.description());

        for (String word : words) {
            tf.put(word, tf.getOrDefault(word, 0.0) + 1);
        }

        int totalWords = words.size();
        tf.replaceAll((w, _) -> tf.get(w) / totalWords);

        return tf;
    }

    public Map<String, Double> computeIDF(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        int size = books.size();

        Set<String> words = new HashSet<>(tokenizer.tokenize(book.description()));

        List<Set<String>> booksWords = books.stream()
            .map(b -> new HashSet<>(tokenizer.tokenize(b.description())))
            .collect(Collectors.toList());

        Map<String, Long> docFrequency = words.stream()
            .collect(Collectors.toMap(
                word -> word,
                word -> booksWords.stream()
                    .filter(b -> b.contains(word))
                    .count()
            ));

        return words.stream()
            .collect(Collectors.toMap(
                word -> word,
                word -> docFrequency.get(word) == 0 ? Math.log(size) :
                    Math.log((double) size / docFrequency.get(word)
                )));
    }

    private double cosineSimilarity(Map<String, Double> first, Map<String, Double> second) {
        double magnitudeFirst = magnitude(first.values());
        double magnitudeSecond = magnitude(second.values());

        return dotProduct(first, second) / (magnitudeFirst * magnitudeSecond);
    }

    private double dotProduct(Map<String, Double> first, Map<String, Double> second) {
        Set<String> commonKeys = new HashSet<>(first.keySet());
        commonKeys.retainAll(second.keySet());

        return commonKeys.stream()
            .mapToDouble(word -> first.get(word) * second.get(word))
            .sum();
    }

    private double magnitude(Collection<Double> input) {
        double squaredMagnitude = input.stream()
            .map(v -> v * v)
            .reduce(0.0, Double::sum);

        return Math.sqrt(squaredMagnitude);
    }
}
