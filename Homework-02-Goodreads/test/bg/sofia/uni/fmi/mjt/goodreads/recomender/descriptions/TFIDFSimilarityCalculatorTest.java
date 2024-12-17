package bg.sofia.uni.fmi.mjt.goodreads.recomender.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.BookLoader;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions.TFIDFSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TFIDFSimilarityCalculatorTest {

    static TFIDFSimilarityCalculator tfidf;

    @BeforeAll
    static void setUp() {
        String stopWordsInput = """
            a
            about
            above
            after
            """;

        String booksInput = """
            a
            0,Title, Author, "Big meat after training","['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",4.27,"5,691,311",https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird
            1,Title 1, Author1, "Above five years and after three big","['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']",4.47,"9,278,135",https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone
            2,Title 2, Author2, "A big about and after meat","['Classics', 'Fiction', 'Romance', 'Historical Fiction', 'Literature', 'Historical', 'Audiobook']",4.28,"3,944,155",https://www.goodreads.com/book/show/1885.Pride_and_Prejudice
            """;

        TextTokenizer tokenizer = new TextTokenizer(new StringReader(stopWordsInput));
        Set<Book> books = BookLoader.load(new StringReader(booksInput));

        tfidf = new TFIDFSimilarityCalculator(books, tokenizer);
    }

    @Test
    void testComputeTFWithNullBook() {
        assertThrows(IllegalArgumentException.class, () -> tfidf.computeTF(null),
            "When books is null computeTF should thrown an IllegalArgumentException");
    }

    @Test
    void testComputeTF() {
        Book book = Book.of(
            new String[] {"0",
                "Title",
                "Author",
                "Big meat after training",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"});

        Map<String, Double> result = Map.of("big", 1.0 / 3, "meat", 1.0 / 3, "training", 1.0 / 3);

        assertEquals(result, tfidf.computeTF(book), "ComputeTF return incorrect result");
    }

    @Test
    void testComputeIDFWithNullBook() {
        assertThrows(IllegalArgumentException.class, () -> tfidf.computeIDF(null),
            "When books is null computeIDF should thrown an IllegalArgumentException");
    }

    @Test
    void testComputeIDF() {
        Book book = Book.of(
            new String[] {"0",
                "Title",
                "Author",
                "Big meat after training with",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"});

        Map<String, Double> result =
            Map.of("big", Math.log(3.0 / 4), "meat", Math.log(3.0 / 3), "training", Math.log(3.0 / 2.0), "with",
                Math.log(3.0));

        assertEquals(result, tfidf.computeIDF(book), "ComputeIDF return incorrect result");
    }

    @Test
    void testComputeTFIDF() {
        Book book = Book.of(
            new String[] {"0",
                "Title",
                "Author",
                "Big meat after training with",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            });

        Map<String, Double> result =
            Map.of(
                "big", Math.log(3.0 / 4) * (1.0 / 4),
                "meat", Math.log(3.0 / 3) * (1.0 / 4),
                "training", Math.log(3.0 / 2.0) * (1.0 / 4),
                "with", Math.log(3.0) * (1.0 / 4));

        assertEquals(result, tfidf.computeTFIDF(book), "ComputeTFIDF return incorrect result");
    }

    @Test
    void testCalculateSimilarity() {
        Book book1 = Book.of(
            new String[] {"0",
                "Title",
                "Author",
                "Big meat after training with",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            });
        Book book2 = Book.of(
            new String[] {"0",
                "Title1",
                "Author1",
                "Big fast food about before",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']"
                , "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            });

        assertEquals(0.03566269721238933, tfidf.calculateSimilarity(book1, book2),
            "CalculateSimilarity return incorrect result");
   }

}
