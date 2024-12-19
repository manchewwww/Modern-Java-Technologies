import bg.sofia.uni.fmi.mjt.goodreads.BookLoader;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.finder.BookFinder;
import bg.sofia.uni.fmi.mjt.goodreads.finder.BookFinderAPI;
import bg.sofia.uni.fmi.mjt.goodreads.finder.MatchOption;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.BookRecommender;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.BookRecommenderAPI;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres.GenresOverlapSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Reader goodreads = new FileReader("goodreads_data.csv");

        Reader stopWords = new FileReader("stopwords.txt");

        TextTokenizer tokenizer = new TextTokenizer(stopWords);
        Set<Book> books = BookLoader.load(goodreads);

        BookFinderAPI bookFinderAPI = new BookFinder(books, tokenizer);

        System.out.println(bookFinderAPI.allBooks().size());
        System.out.println(bookFinderAPI.allGenres().size());
        System.out.println(bookFinderAPI.searchByGenres(Set.of("Fiction"), MatchOption.MATCH_ALL).size());
        System.out.println(bookFinderAPI.searchByKeywords(Set.of("unforgettable"), MatchOption.MATCH_ANY).size());

        SimilarityCalculator similarityCalculator = new GenresOverlapSimilarityCalculator();
        BookRecommenderAPI bookRecommenderAPI = new BookRecommender(books, similarityCalculator);

        Book book =
            Book.of(new String[] {"1", "Harry Potter and the Philosopher’s Stone (Harry Potter, #1)", "J.K.Rowling",
                "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']", "4.47",
                "9,278,135", "https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone"
            });

        System.out.printf("%s\n", bookRecommenderAPI.recommendBooks(book, 1));

    }
}
