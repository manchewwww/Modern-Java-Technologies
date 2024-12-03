package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FrequencyRuleTest {

    @Test
    public void testConstructorWithInvalidTransactionCountThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new FrequencyRule(0, Duration.ofMinutes(10), 0.1),
            "When transactionCountThreshold is lower than 1 an IllegalArgumentException should be thrown");
    }

    @Test
    public void testConstructorWithInvalidTimeWindow() {
        assertThrows(IllegalArgumentException.class, () -> new FrequencyRule(1, null, 0.1),
            "When timeWindow is null an IllegalArgumentException should be thrown");
    }

    @Test
    public void testConstructorWithInvalidWeight() {
        assertThrows(IllegalArgumentException.class, () -> new FrequencyRule(2, Duration.ofMinutes(10), 1.5),
            "When weight is lower than 0 or bigger than 1 an IllegalArgumentException should be thrown");
    }

    @Test
    public void testApplicableWithNullTransactions() {
        Rule rule = new FrequencyRule(2, Duration.ofMinutes(1), 1);

        assertThrows(IllegalArgumentException.class, () -> rule.applicable(null),
            "When transactions is null an IllegalArgumentException should be thrown");
    }

    @Test
    public void testApplicableWithEmptyTransactions() {
        Rule rule = new FrequencyRule(2, Duration.ofMinutes(1), 1);

        List<Transaction> transactions = List.of();

        assertFalse(rule.applicable(transactions), "Frequency rule should not be applicable when transaction is empty");
    }

    @Test
    public void testNonApplicableWithLowerTransactionsSizeFromTransactionCountThreshold() {
        Rule rule = new FrequencyRule(4, Duration.ofMinutes(1), 1);

        LocalDateTime now = LocalDateTime.now();
        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10.0, now.minusMinutes(2), "San diego", Channel.ATM),
            new Transaction("2", "account2", 20.0, now.minusMinutes(1), "San diego", Channel.ATM)
        );

        assertFalse(rule.applicable(transactions),
            "Frequency rule should not be applicable when transaction in period is lower than transactionCountThreshold");
    }

    @Test
    public void testApplicable() {
        Rule rule = new FrequencyRule(2, Duration.ofMinutes(10), 1);

        LocalDateTime now = LocalDateTime.now();
        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10.0, now.minusMinutes(2), "San diego", Channel.ATM),
            new Transaction("2", "account2", 20.0, now.minusMinutes(1), "San diego", Channel.ATM)
        );

        assertTrue(rule.applicable(transactions),
            "Frequency rule should be applicable when transaction in period is bigger or equal to transactionCountThreshold");
    }

    @Test
    public void testNonApplicable() {
        Rule rule = new FrequencyRule(2, Duration.ofMinutes(1), 1);

        LocalDateTime now = LocalDateTime.now();
        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10.0, now.minusMinutes(2), "San diego", Channel.ATM),
            new Transaction("2", "account2", 20.0, now.minusMinutes(1), "San diego", Channel.ATM)
        );

        assertFalse(rule.applicable(transactions),
            "Frequency rule should not be applicable when transaction in period is lower than transactionCountThreshold");
    }
}
