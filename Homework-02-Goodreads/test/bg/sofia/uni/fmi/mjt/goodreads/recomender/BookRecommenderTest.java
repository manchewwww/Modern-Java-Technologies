package bg.sofia.uni.fmi.mjt.goodreads.recomender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.BookRecommender;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.BookRecommenderAPI;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres.GenresOverlapSimilarityCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookRecommenderTest {

    private static Book origin;
    private static BookRecommenderAPI recommender;
    private static Book book1;
    private static Book book2;

    @BeforeAll
    public static void setUp() {
        origin = Book.of(
            new String[] {"0",
                "Title",
                "Author",
                "Big meat after training with",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            }
        );
        SimilarityCalculator similarityCalculator = new GenresOverlapSimilarityCalculator();

        book1 = Book.of(
            new String[] {"1",
                "Title1",
                "Author1",
                "Big meat after training with",
                "['Fiction', 'Historical Fiction', 'Literature', 'Young Adult', 'Historical']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            }
        );
        book2 = Book.of(
            new String[] {"0",
                "Title1",
                "Author1",
                "Big fast food about before",
                "['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            });

        Set<Book> books = Set.of(book1, book2);
        recommender = new BookRecommender(books, similarityCalculator);
    }

    @Test
    void testRecommendBooksWithNullOriginBook() {
        assertThrows(IllegalArgumentException.class, () -> recommender.recommendBooks(null, 10),
            "When origin is null recommend books should return IllegalArgumentException");
    }

    @Test
    void testRecommendBooksWithZeroMaxN() {
        assertThrows(IllegalArgumentException.class, () -> recommender.recommendBooks(origin, 0),
            "When max N is zero or negative recommend books should return IllegalArgumentException");
    }

    @Test
    void testRecommendBooksWithAllBooks() {
        SortedMap<Book, Double> result = new TreeMap<>((_, _) -> Double.compare(2, 1));
        result.put(book1, 1.0);
        result.put(book2, 0.42857142857142855);

        assertEquals(result, recommender.recommendBooks(origin, 2),
            "RecommendBooks return incorrect result. They aren't sorted correct");
    }

    @Test
    void testRecommendBooksWithOneMaxN() {
        SortedMap<Book, Double> result = new TreeMap<>((_, _) -> Double.compare(2, 1));
        result.put(book1, 1.0);

        assertEquals(result, recommender.recommendBooks(origin, 1),
            "RecommendBooks return incorrect result. They aren't sorted correct");
    }

}
