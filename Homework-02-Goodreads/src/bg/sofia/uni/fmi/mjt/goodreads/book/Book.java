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

    static final int ID_INDEX = 0;
    static final int TITLE_INDEX = 1;
    static final int AUTHOR_INDEX = 2;
    static final int DESCRIPTION_INDEX = 3;
    static final int GENRES_INDEX = 4;
    static final int RATING_INDEX = 5;
    static final int RATING_COUNT_INDEX = 6;
    static final int URL_INDEX = 7;

    public static Book of(String[] tokens) {
        final int tokensSize = 8;
        if (tokens == null) {
            throw new IllegalArgumentException("tokens is null");
        }
        if (tokens.length != tokensSize) {
            throw new IllegalArgumentException("Tokens size should be " + tokensSize);
        }

        List<String> genres =
            Arrays.stream(tokens[GENRES_INDEX].substring(1, tokens[GENRES_INDEX].length() - 1).split(","))
                .map(s -> s.replaceAll("'", ""))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();

        return new Book(tokens[ID_INDEX], tokens[TITLE_INDEX], tokens[AUTHOR_INDEX], tokens[DESCRIPTION_INDEX],
            genres, Double.parseDouble(tokens[RATING_INDEX]),
            Integer.parseInt(tokens[RATING_COUNT_INDEX].replaceAll(",", "")), tokens[URL_INDEX]);
    }

}
