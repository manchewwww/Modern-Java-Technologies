package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.FrequencyRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.LocationsRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.SmallTransactionsRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.ZScoreRule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionAnalyzerImplTest {

    private static Reader reader;
    private static TransactionAnalyzerImpl transactionAnalyzer;

    @BeforeAll
    public static void setUp() {
        List<Rule> rules = List.of(
            new ZScoreRule(0.5, 0.3),
            new LocationsRule(3, 0.4),
            new FrequencyRule(4, Period.ofWeeks(4), 0.25),
            new SmallTransactionsRule(1, 10.20, 0.05)
        );

        String inputData = """
            transactionId,accountId,amount,transactionDate,location,channel
            1,account1,10.0,2023-12-04 10:00:00,Vienna,ATM
            2,account2,20.0,2023-12-04 10:01:00,Sofia,Online
            3,account3,20.0,2023-12-04 10:02:00,Sofia,Branch
            4,account1,20.0,2023-12-04 10:03:00,Rome,ATM
            """;

        reader = new StringReader(inputData);

        transactionAnalyzer = new TransactionAnalyzerImpl(reader, rules);
    }

    @Test
    public void testCreationWithInvalidReader() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionAnalyzerImpl(null, new ArrayList<>()),
            "When reader is null an InvalidArgumentException should be thrown");
    }

    @Test
    public void testCreationWithInvalidRules() {
        assertThrows(IllegalArgumentException.class,
            () -> new TransactionAnalyzerImpl(reader, null),
            "When rules is null an InvalidArgumentException should be thrown");
    }

    @Test
    public void testCreationWithInvalidRulesWeight() {
        List<Rule> rules = List.of(
            new ZScoreRule(1.5, 0.3),
            new LocationsRule(3, 0.4),
            new FrequencyRule(4, Period.ofWeeks(4), 0.25),
            new SmallTransactionsRule(1, 10.20, 0.01)
        );

        assertThrows(IllegalArgumentException.class,
            () -> new TransactionAnalyzerImpl(reader, rules),
            "When rules weight is different form 1 an InvalidArgumentException should be thrown");
    }

    @Test
    public void testAllTransactions() {
        List<Transaction> transactions = List.of(
            new Transaction("1", "account1", 10.0, LocalDateTime.of(2023, 12, 4, 10, 0, 0), "Vienna", Channel.ATM),
            new Transaction("2", "account2", 20.0, LocalDateTime.of(2023, 12, 4, 10, 1, 0), "Sofia", Channel.ONLINE),
            new Transaction("3", "account3", 20.0, LocalDateTime.of(2023, 12, 4, 10, 2, 0), "Sofia", Channel.BRANCH),
            new Transaction("4", "account1", 20.0, LocalDateTime.of(2023, 12, 4, 10, 3, 0), "Rome", Channel.ATM)
            );

        assertIterableEquals(transactions, transactionAnalyzer.allTransactions(), "Transactions is set incorrect because getter return other result");
    }

    @Test
    public void testAllAccountIDs() {
        List<String> accountIDs = List.of("account1", "account2", "account3");

        assertIterableEquals(accountIDs, transactionAnalyzer.allAccountIDs(),
            "Account ids is incorrect return in all account ids");
    }

    @Test
    public void testTransactionCountByChannel() {
        Map<Channel, Integer> result = new HashMap<>();
        result.put(Channel.ATM, 2);
        result.put(Channel.ONLINE, 1);
        result.put(Channel.BRANCH, 1);

        assertEquals(result, transactionAnalyzer.transactionCountByChannel(),
            "Transaction count by channel return different result in grouping");
    }

    @Test
    public void testAmountSpentByUser() {
        final int result = 30;

        assertEquals(result, transactionAnalyzer.amountSpentByUser("account1"),
            "Amount spent by account 1 is different from expected result. Invalid calculation or creation");
    }

    @Test
    public void testAllTransactionsByUser() {
        List<Transaction> result = List.of(
            new Transaction("2", "account2", 20.0, LocalDateTime.of(2023, 12, 4, 10, 1, 0), "Sofia", Channel.ONLINE)
        );

        assertIterableEquals(result, transactionAnalyzer.allTransactionsByUser("account2"),
            "Transactions by user is return incorrect result. The problem is in taking transaction in function");
    }

    @Test
    public void testAccountRatingWithUserWithEmptyTransactions() {
        assertEquals(0.0, transactionAnalyzer.accountRating("account4"),
            "Account rating should return 0 because this user don't have any transactions");
    }

    @Test
    public void testAccountRating() {
        assertEquals(0.0, transactionAnalyzer.accountRating("account2"),
            "Account 2 rating should be 0 because no one rule is applicable");

        assertEquals(0.35, transactionAnalyzer.accountRating("account1"),
            "Account 1 rating should be 0.35 because [SmallTransactionsRule + ZScoreRule] = 0.35");
    }

    @Test
    public void testAccountsRisk() {
        SortedMap<String, Double> result = new TreeMap<>();
        result.put("account1", 0.35);
        result.put("account2", 0.0);
        result.put("account3", 0.0);

        assertEquals(result, transactionAnalyzer.accountsRisk(),
            "Accounts risk function return incorrect result.");
    }

}
