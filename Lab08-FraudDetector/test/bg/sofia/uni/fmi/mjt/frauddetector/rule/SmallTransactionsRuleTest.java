package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmallTransactionsRuleTest {

    @Test
    public void testConstructorWithInvalidCountThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new SmallTransactionsRule(0, 10.0, 0.3),
            "When count threshold is less than 1 an IllegalArgumentException should be thrown");
    }

    @Test
    public void testConstructorWithInvalidAmountThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new SmallTransactionsRule(2, 0.0, 0.3),
            "When amount threshold is less or equal to 0.0 an IllegalArgumentException should be thrown");
    }

    @Test
    public void testConstructorWithInvalidWeight() {
        assertThrows(IllegalArgumentException.class, () -> new SmallTransactionsRule(2, 10.0, -1),
            "When weight is less than 0 or greater than 1 an IllegalArgumentException should be thrown");
    }

    @Test
    public void testApplicableWithNullTransactions() {
        Rule rule = new SmallTransactionsRule(2, 10, 0.5);

        assertThrows(IllegalArgumentException.class, () -> rule.applicable(null),
            "When transactions is null IllegalArgumentException should be thrown");
    }

    @Test
    public void testApplicableWithEmptyTransactions() {
        Rule rule = new SmallTransactionsRule(2, 10, 0.5);

        assertFalse(rule.applicable(new ArrayList<>()),
            "Small transactions rule should not be applicable when transactions is empty");
    }

    @Test
    public void testApplicableWithLessTransactionsSizeThanThreshold() {
        Rule rule = new SmallTransactionsRule(3, 10, 0.5);

        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10.0, LocalDateTime.now().minusMinutes(2), "San diego", Channel.ATM),
            new Transaction("2", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "San diego", Channel.ATM)
        );

        assertFalse(rule.applicable(transactions),
            "Small transactions rule should not be applicable when transactions size is less than threshold");
    }

    @Test
    void testApplicable() {
        Rule rule = new SmallTransactionsRule(3, 20, 0.5);

        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10.0, LocalDateTime.now().minusMinutes(2), "Sofia", Channel.ATM),
            new Transaction("2", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Vienna", Channel.ATM),
            new Transaction("3", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Dubai", Channel.ATM)
        );

        assertTrue(rule.applicable(transactions),
            "Small transactions rule should be applicable when transactions, with less amount than amount threshold, are greater or equal to threshold");
    }

    @Test
    void testNonApplicable() {
        Rule rule = new SmallTransactionsRule(3, 5, 0.5);

        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10.0, LocalDateTime.now().minusMinutes(2), "Sofia", Channel.ATM),
            new Transaction("2", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Sofia", Channel.ATM),
            new Transaction("3", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Dubai", Channel.ATM)
        );

        assertFalse(rule.applicable(transactions),
            "Small transactions rule should be non applicable when transactions, with less amount than amount threshold, are less than threshold");
    }

}
