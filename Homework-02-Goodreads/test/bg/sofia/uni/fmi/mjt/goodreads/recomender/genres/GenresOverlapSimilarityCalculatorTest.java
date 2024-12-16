package bg.sofia.uni.fmi.mjt.goodreads.recomender.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres.GenresOverlapSimilarityCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenresOverlapSimilarityCalculatorTest {

    static SimilarityCalculator similarityCalculator = new GenresOverlapSimilarityCalculator();
    static Book firstBook;
    static Book secondBook;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        firstBook = Book.of(
            new String[] {"0",
                "Title",
                "Author",
                "Big meat after training with",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            });

        secondBook = Book.of(
            new String[] {"0",
                "Title1",
                "Author1",
                "Big fast food about before",
                "['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            });
    }

    @Test
    public void testSimilarityWithNullFirstBook() {
        assertThrows(IllegalArgumentException.class, () -> similarityCalculator.calculateSimilarity(null, secondBook),
            "When first book is null should throw a IllegalArgumentException");
    }

    @Test
    public void testSimilarityWithNullSecondBook() {
        assertThrows(IllegalArgumentException.class, () -> similarityCalculator.calculateSimilarity(firstBook, null),
            "When second book is null should throw a IllegalArgumentException");
    }

    @Test
    public void testSimilarityWithSameGenres() {
        Book sameBook = Book.of(
            new String[] {"4",
                "Title3",
                "Author3",
                "Big meat after training with",
                "['Classics', 'Fiction', 'School', 'Literature', 'Young Adult', 'Historical']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            });

        assertEquals(1, similarityCalculator.calculateSimilarity(firstBook, sameBook),
            "When book genres are subset of other program should return 1");
    }

    @Test
    public void testSimilarityWithEmptyGenresBook() {
        Book emptyBook = Book.of(
            new String[] {"4",
                "Title3",
                "Author3",
                "Big meat after training with",
                "[]"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            });

        assertEquals(0, similarityCalculator.calculateSimilarity(firstBook, emptyBook),
            "When book genres are empty of other program should return 1");
    }

    @Test
    public void testSimilarity() {
        assertEquals(0.42857142857142855, similarityCalculator.calculateSimilarity(firstBook, secondBook),
            "Genres similarity calculator return incorrect result");
    }

}
