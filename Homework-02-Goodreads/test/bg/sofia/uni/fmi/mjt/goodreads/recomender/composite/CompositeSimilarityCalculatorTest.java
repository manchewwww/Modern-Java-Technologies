package bg.sofia.uni.fmi.mjt.goodreads.recomender.composite;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite.CompositeSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions.TFIDFSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres.GenresOverlapSimilarityCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeSimilarityCalculatorTest {

    private static SimilarityCalculator similarityCalculator;
    private static Book firstBook;
    private static Book secondBook;

    @BeforeAll
    static void setUp() {
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

        Map<SimilarityCalculator, Double> similarityCalculatorMap = new HashMap<>();
        TFIDFSimilarityCalculator tfidfSimilarityCalculator = mock();
        GenresOverlapSimilarityCalculator genresOverlapSimilarityCalculator = mock();

        when(tfidfSimilarityCalculator.calculateSimilarity(firstBook, secondBook)).thenReturn(0.65);
        when(genresOverlapSimilarityCalculator.calculateSimilarity(firstBook, secondBook)).thenReturn(0.33);

        similarityCalculatorMap.put(tfidfSimilarityCalculator, 0.2);
        similarityCalculatorMap.put(genresOverlapSimilarityCalculator, 0.3);

        similarityCalculator = new CompositeSimilarityCalculator(similarityCalculatorMap);
    }

    @Test
    public void testSimilarityWithFirstBookNull() {
        assertThrows(IllegalArgumentException.class, () -> similarityCalculator.calculateSimilarity(null, secondBook),
            "When first book is null IllegalArgumentException should be thrown");
    }

    @Test
    public void testSimilarityWithSecondBookNull() {
        assertThrows(IllegalArgumentException.class, () -> similarityCalculator.calculateSimilarity(firstBook, null),
            "When second book is null IllegalArgumentException should be thrown");
    }

    @Test
    public void testSimilarity() {
        assertEquals(0.229, similarityCalculator.calculateSimilarity(firstBook, secondBook),
            "Similarity calculation return incorrect result");
    }


}
