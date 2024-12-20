package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookFinder implements BookFinderAPI {

    private final Set<Book> books;
    private final TextTokenizer tokenHandler;

    public BookFinder(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenHandler = tokenizer;
    }

    public Set<Book> allBooks() {
        return books;
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        return books.stream()
            .filter(b -> b.author().equals(authorName))
            .toList();
    }

    @Override
    public Set<String> allGenres() {
        return books.stream()
            .flatMap(b -> b.genres().stream())
            .collect(Collectors.toSet());
    }

    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        if (genres == null) {
            throw new IllegalArgumentException("Genres must not be null");
        }

        if (option == MatchOption.MATCH_ALL) {
            return books.stream()
                .filter(b -> new HashSet<>(b.genres()).containsAll(genres))
                .toList();
        } else {
            return books.stream()
                .filter(b -> b.genres().stream().anyMatch(genres::contains))
                .toList();
        }
    }

    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        if (keywords == null) {
            throw new IllegalArgumentException("Keywords must not be null");
        }

        if (option == MatchOption.MATCH_ALL) {
            return books.stream()
                .filter(b -> new HashSet<>(tokenHandler.tokenize(b.description())).containsAll(keywords))
                .toList();
        } else {
            return books.stream()
                .filter(b -> tokenHandler.tokenize(b.description()).stream().anyMatch(keywords::contains))
                .toList();
        }
    }

}