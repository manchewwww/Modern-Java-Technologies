package bg.sofia.uni.fmi.mjt.goodreads.book;

import java.util.Arrays;
import java.util.List;

public record Book(
    String ID,
    String title,
    String author,
    String description,
    List<String> genres,
    double rating,
    int ratingCount,
    String URL
) {

    public static Book of(String[] tokens) {
        final int tokensSize = 8;
        if (tokens == null) {
            throw new IllegalArgumentException("tokens is null");
        }
        if (tokens.length != tokensSize) {
            throw new IllegalArgumentException("Tokens size should be " + tokensSize);
        }

        final int iDIndex = 0;
        final int titleIndex = 1;
        final int authorIndex = 2;
        final int descriptionIndex = 3;
        final int genresIndex = 4;
        final int ratingIndex = 5;
        final int ratingCountIndex = 6;
        final int uRLIndex = 7;

        List<String> genres = Arrays.stream(tokens[genresIndex].substring(1, tokens[genresIndex].length() - 1).split(","))
            .map(s -> s.replaceAll("'", ""))
            .map(String::trim)
            .filter(s -> !s.isBlank())
            .toList();

        return new Book(tokens[iDIndex], tokens[titleIndex], tokens[authorIndex], tokens[descriptionIndex],
            genres, Double.parseDouble(tokens[ratingIndex]),
            Integer.parseInt(tokens[ratingCountIndex]), tokens[uRLIndex]);
    }

}
