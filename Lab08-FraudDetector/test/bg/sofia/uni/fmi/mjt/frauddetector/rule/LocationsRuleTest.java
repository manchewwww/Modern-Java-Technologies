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

public class LocationsRuleTest {

    @Test
    public void testConstructorWithInvalidThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new LocationsRule(-1, 1),
            "When threshold is less than 1 an IllegalArgumentException should be thrown");
    }

    @Test
    public void testConstructorWithInvalidWeight() {
        assertThrows(IllegalArgumentException.class, () -> new LocationsRule(2, 2),
            "When weight is less than 0 or greater than 1 an IllegalArgumentException should be thrown");
    }

    @Test
    public void testApplicableWithNullTransactions() {
        Rule rule = new LocationsRule(2, 0.5);

        assertThrows(IllegalArgumentException.class, () -> rule.applicable(null),
            "When transactions is null IllegalArgumentException should be thrown");
    }

    @Test
    public void testApplicableWithEmptyTransactions() {
        Rule rule = new LocationsRule(2, 0.5);
        assertFalse(rule.applicable(new ArrayList<>()), "Location rule should not be applicable when transactions is empty");
    }

    @Test
    public void testApplicableWithLessTransactionsSizeThanThreshold() {
        Rule rule = new LocationsRule(3, 0.5);

        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10.0, LocalDateTime.now().minusMinutes(2), "San diego", Channel.ATM),
            new Transaction("2", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "San diego", Channel.ATM)
        );

        assertFalse(rule.applicable(transactions), "Location rule should not be applicable when transactions size is less than threshold");
    }

    @Test
    void testApplicable() {
        Rule rule = new LocationsRule(3, 0.5);


        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10.0, LocalDateTime.now().minusMinutes(2), "Sofia", Channel.ATM),
            new Transaction("2", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Vienna", Channel.ATM),
            new Transaction("2", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Dubai", Channel.ATM)
        );

        assertTrue(rule.applicable(transactions), "Location rule should be applicable when transactions from different locations are greater or equal to threshold");
    }

    @Test
    void testNonApplicable() {
        Rule rule = new LocationsRule(3, 0.5);


        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10.0, LocalDateTime.now().minusMinutes(2), "Sofia", Channel.ATM),
            new Transaction("2", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Sofia", Channel.ATM),
            new Transaction("3", "account1", 20.0, LocalDateTime.now().minusMinutes(1), "Dubai", Channel.ATM)
        );

        assertFalse(rule.applicable(transactions), "Location rule should not be applicable when transactions from different locations are less than threshold");
    }

}
