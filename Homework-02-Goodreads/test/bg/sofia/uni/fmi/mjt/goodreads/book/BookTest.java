package bg.sofia.uni.fmi.mjt.goodreads.book;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookTest {

    @Test
    public void testBookOfWithNullTokens() {
        assertThrows(IllegalArgumentException.class, () -> Book.of(null),
            "When tokens are null book should throw IllegalArgumentException");
    }

    @Test
    public void testBookOfWithTokensWithDifferentSize() {
        assertThrows(IllegalArgumentException.class, () -> Book.of(new String[] {"author", "desc"}),
            "When tokens are null book should throw IllegalArgumentException");
    }

    @Test
    public void testBookOf() {
        Book book =
            Book.of(new String[] {"1", "Harry Potter and the Philosopher’s Stone (Harry Potter, #1)", "J.K.Rowling",
                "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!",
                "['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']", "4.47",
                "9,278,135", "https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone"
            });

        List<String> genres = List.of("Fantasy", "Fiction", "Young Adult", "Magic", "Childrens", "Middle Grade", "Classics");

        assertEquals("1", book.ID(), "ID is set incorrect");
        assertEquals("Harry Potter and the Philosopher’s Stone (Harry Potter, #1)", book.title(),
            "Title is set incorrect");
        assertEquals("J.K.Rowling", book.author(), "Author is set incorrect");
        assertEquals("Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!",
            book.description(), "Description is set incorrect");
        assertIterableEquals(genres, book.genres(), "Genres are set incorrect");
        assertEquals(4.47, book.rating(), "Rating is set incorrect");
        assertEquals(9278135, book.ratingCount(), "Rating count is set incorrect");
        assertEquals("https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone",
            book.URL(), "Url is set incorrect");

    }

}
