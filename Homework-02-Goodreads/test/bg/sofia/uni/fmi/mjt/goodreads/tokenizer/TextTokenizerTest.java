package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextTokenizerTest {

    private static TextTokenizer tokenizer;

    @BeforeAll
    static void setUpBeforeClass(){
        String input = """
            a
            about
            above
            after
            """;

        tokenizer = new TextTokenizer(new StringReader(input));
    }

    @Test
    public void testTokenizeWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> tokenizer.tokenize(null),
            "When input is null tokenize should thrown IllegalArgumentException");
    }

    @Test
    public void testTokenizeWithEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> tokenizer.tokenize(" "),
            "When input is empty tokenize should thrown IllegalArgumentException");
    }

    @Test
    public void testTokenize() {
        String input = "I learn about programming after math";
        List<String> result = List.of("i", "learn", "programming", "math");
        assertIterableEquals(result, tokenizer.tokenize(input), "Result after tokenize is incorrect");
    }

}
