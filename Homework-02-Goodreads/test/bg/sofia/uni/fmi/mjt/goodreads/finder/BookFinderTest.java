package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.BookLoader;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookFinderTest {

    private static BookFinderAPI bookFinder;

    @BeforeAll
    public static void setUp() {
        String stopWordsInput = """
            a
            about
            above
            after
            """;

        String booksInput = """
            a
            0,Title,Author, "Big meat after training","['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",4.27,"5,691,311",https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird
            4,Bar,MJT, "Foo","['2024']",4.27,"5,691,311",https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird
            1,T,Author1, "Above five years and after three big","['Fantasy', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']",4.47,"9,278,135",https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone
            2,Title 2,Author, "A big about and after meat","['Classics', 'Fiction', 'Romance', 'Historical Fiction', 'Literature', 'Historical', 'Audiobook']",4.28,"3,944,155",https://www.goodreads.com/book/show/1885.Pride_and_Prejudice
            """;

        TextTokenizer tokenizer = new TextTokenizer(new StringReader(stopWordsInput));
        Set<Book> books = BookLoader.load(new StringReader(booksInput));

        bookFinder = new BookFinder(books, tokenizer);
    }

    @Test
    public void testSearchByAuthor() {
        List<Book> result = List.of(Book.of(new String[] {
            "0", "Title", "Author", "Big meat after training",
            "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",
            "4.27", "5,691,311", "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        }), Book.of(new String[] {
            "2", "Title 2", "Author", "A big about and after meat",
            "['Classics', 'Fiction', 'Romance', 'Historical Fiction', 'Literature', 'Historical', 'Audiobook']",
            "4.28", "3,944,155", "https://www.goodreads.com/book/show/1885.Pride_and_Prejudice"
        }));

        assertTrue(result.containsAll(bookFinder.searchByAuthor("Author")),
            "Books find by author return incorrect result");
    }

    @Test
    public void testAllGenres() {
        Set<String> result =
            Set.of("Classics", "Fiction", "Historical Fiction", "School", "Literature", "Young Adult", "Historical",
                "Fantasy", "Magic", "Childrens", "Middle Grade", "Romance", "Audiobook", "2024");

        assertTrue(result.containsAll(bookFinder.allGenres()), "Genres are set incorrect");
    }

    @Test
    public void testSearchByGenresWithNullGenres() {
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByGenres(null, MatchOption.MATCH_ALL),
            "When genres are null search by genres should thrown IllegalArgumentException");
    }

    @Test
    public void testSearchByGenresWithMatchAll() {
        List<Book> result = List.of(Book.of(new String[] {
            "0", "Title", "Author", "Big meat after training",
            "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",
            "4.27", "5,691,311", "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        }), Book.of(new String[] {
            "2", "Title 2", "Author", "A big about and after meat",
            "['Classics', 'Fiction', 'Romance', 'Historical Fiction', 'Literature', 'Historical', 'Audiobook']",
            "4.28", "3,944,155", "https://www.goodreads.com/book/show/1885.Pride_and_Prejudice"
        }));

        Set<String> genres = Set.of("Classics", "Fiction");

        assertTrue(result.containsAll(bookFinder.searchByGenres(genres, MatchOption.MATCH_ALL)) ,
            "Search by genres with mach all option return incorrect result");
    }

    @Test
    public void testSearchByGenresWithMatchAny() {
        List<Book> result = List.of(Book.of(new String[] {
            "0", "Title", "Author", "Big meat after training",
            "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",
            "4.27", "5,691,311", "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        }), Book.of(new String[] {
            "1", "T", "Author1", "Above five years and after three big",
            "['Fantasy', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']",
            "4.47", "9,278,135",
            "https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone"
        }), Book.of(new String[] {
            "2", "Title 2", "Author", "A big about and after meat",
            "['Classics', 'Fiction', 'Romance', 'Historical Fiction', 'Literature', 'Historical', 'Audiobook']",
            "4.28", "3,944,155", "https://www.goodreads.com/book/show/1885.Pride_and_Prejudice"
        }));

        Set<String> genres = Set.of("Historical Fiction", "Young Adult");

        assertTrue(result.containsAll(bookFinder.searchByGenres(genres, MatchOption.MATCH_ANY)),
            "Search by genres with mach any option return incorrect result");
    }

    @Test
    public void testSearchByKeywordsWithNullKeywords() {
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByKeywords(null, MatchOption.MATCH_ALL),
            "When keywords are null search by keywords should thrown IllegalArgumentException");
    }

    @Test
    public void testSearchByKeywordsWithMatchAll() {
        List<Book> result = List.of(Book.of(new String[] {
            "0", "Title", "Author", "Big meat after training",
            "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",
            "4.27", "5,691,311", "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        }));

        Set<String> keywords = Set.of("big", "meat", "training");

        assertIterableEquals(result, bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ALL),
            "Search by keywords with mach all option return incorrect result");
    }

    @Test
    public void testSearchByKeywordsWithMatchAny() {
        List<Book> result = List.of(Book.of(new String[] {
            "0", "Title", "Author", "Big meat after training",
            "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",
            "4.27", "5,691,311", "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        }), Book.of(new String[] {
            "2", "Title 2", "Author", "A big about and after meat",
            "['Classics', 'Fiction', 'Romance', 'Historical Fiction', 'Literature', 'Historical', 'Audiobook']",
            "4.28", "3,944,155", "https://www.goodreads.com/book/show/1885.Pride_and_Prejudice"
        }));

        Set<String> keywords = Set.of("meat", "ok");

        assertIterableEquals(result, bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ANY),
            "Search by keywords with mach any option return incorrect result");
    }

    @Test
    public void testSearchByKeywordsWithWordsFromTitle() {
        List<Book> result = List.of(Book.of(new String[] {
            "0", "Title", "Author", "Big meat after training",
            "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",
            "4.27", "5,691,311", "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        }), Book.of(new String[] {
            "2", "Title 2", "Author", "A big about and after meat",
            "['Classics', 'Fiction', 'Romance', 'Historical Fiction', 'Literature', 'Historical', 'Audiobook']",
            "4.28", "3,944,155", "https://www.goodreads.com/book/show/1885.Pride_and_Prejudice"
        }));

        Set<String> keywords = Set.of("title");

        assertIterableEquals(result, bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ANY),
            "Search by keywords with mach any option return incorrect result");
    }

    @Test
    public void testSearchByKeywordsWithCombineWords() {
        List<Book> result = List.of(Book.of(new String[] {
            "4", "Bar", "MJT", "Foo",
            "['2024']",
            "4.27", "5,691,311", "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        }));

        Set<String> keywords = Set.of("bar", "foo");

        assertIterableEquals(result, bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ALL),
            "Search by keywords with mach any option return incorrect result");
    }

}
