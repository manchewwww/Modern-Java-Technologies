package bg.sofia.uni.fmi.mjt.news.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArgumentsTest {

    @Test
    public void testBuilderWithNull() {
        assertThrows(IllegalArgumentException.class, ()-> Arguments.builder(null),
            "Keyword in builder cannot be null");
    }

}
