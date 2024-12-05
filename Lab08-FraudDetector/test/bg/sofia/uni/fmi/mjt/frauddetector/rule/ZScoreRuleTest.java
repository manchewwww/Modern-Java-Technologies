package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZScoreRuleTest {

    //public ZScoreRule(double zScoreThreshold, double weight) {

    @Test
    void testConstructorWithInvalidZScoreThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new ZScoreRule(-1, 1),
            "When zScore threshold is less than 0 an IllegalArgumentException should be thrown");
    }

    @Test
    void testConstructorWithInvalidWeight() {
        assertThrows(IllegalArgumentException.class, () -> new ZScoreRule(1, -10),
            "When weight is less than 0 or greater than 1 an IllegalArgumentException should be thrown");
    }

    @Test
    public void testApplicableWithNullTransactions() {
        Rule rule = new ZScoreRule(10, 0.5);

        assertThrows(IllegalArgumentException.class, () -> rule.applicable(null),
            "When transactions is null IllegalArgumentException should be thrown");
    }

    @Test
    public void testApplicableWithEmptyTransactions() {
        Rule rule = new ZScoreRule(10, 0.5);

        assertFalse(rule.applicable(new ArrayList<>()),
            "Z score rule should not be applicable when transactions is empty");
    }

    @Test
    void testApplicable() {
        Rule rule = new ZScoreRule(1, 0.5);

        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 100.0, LocalDateTime.now().minusMinutes(2), "Sofia", Channel.ATM),
            new Transaction("2", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Vienna", Channel.ATM),
            new Transaction("3", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Dubai", Channel.ATM)
        );

        assertTrue(rule.applicable(transactions),
            "Z score rule should be applicable when one z score is greater than z score threshold");
    }

    @Test
    void testNonApplicable() {
        Rule rule = new ZScoreRule(1, 0.5);

        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10, LocalDateTime.now().minusMinutes(2), "Sofia", Channel.ATM),
            new Transaction("2", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Vienna", Channel.ATM),
            new Transaction("3", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Dubai", Channel.ATM)
        );

        assertFalse(rule.applicable(transactions),
            "Z score rule should not be applicable when no one z score is greater than z score threshold");
    }

    @Test
    void testNonApplicableWithStandardDeviation() {
        Rule rule = new ZScoreRule(1, 0.5);

        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 20.0, LocalDateTime.now().minusMinutes(2), "Sofia", Channel.ATM),
            new Transaction("2", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Vienna", Channel.ATM),
            new Transaction("3", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Dubai", Channel.ATM)
        );

        assertFalse(rule.applicable(transactions),
            "Z score rule should not be applicable when no one z score is greater than z score threshold");
    }
}
