package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    @Test
    public void testTransactionSetWithATMChannel() {
        Transaction transaction = Transaction.of("TX000001,AC00128,14.09,2023-04-11 16:29:14,San Diego,ATM");

        LocalDateTime time = LocalDateTime.of(2023, 4, 11, 16, 29, 14);

        assertEquals("TX000001", transaction.transactionID(), "Transaction ID is set incorrect");
        assertEquals("AC00128", transaction.accountID(), "Account ID is set incorrect");
        assertEquals(14.09, transaction.transactionAmount(), "Transaction amount is set incorrect");
        assertEquals(time, transaction.transactionDate(), "Transaction date is set incorrect");
        assertEquals("San Diego", transaction.location(), "Transaction ID is set incorrect");
        assertEquals(Channel.ATM, transaction.channel(), "ATM channel is set incorrect");
    }

    @Test
    public void testTransactionSetWithOnlineChannel() {
        Transaction transaction = Transaction.of("TX000001,AC00128,14.09,2023-04-11 16:29:14,San Diego,Online");

        LocalDateTime time = LocalDateTime.of(2023, 4, 11, 16, 29, 14);

        assertEquals("TX000001", transaction.transactionID(), "Transaction ID is set incorrect");
        assertEquals("AC00128", transaction.accountID(), "Account ID is set incorrect");
        assertEquals(14.09, transaction.transactionAmount(), "Transaction amount is set incorrect");
        assertEquals(time, transaction.transactionDate(), "Transaction date is set incorrect");
        assertEquals("San Diego", transaction.location(), "Transaction ID is set incorrect");
        assertEquals(Channel.ONLINE, transaction.channel(), "Online channel is set incorrect");
    }

    @Test
    public void testTransactionSetWithBranchChannel() {
        Transaction transaction = Transaction.of("TX000001,AC00128,14.09,2023-04-11 16:29:14,San Diego,Branch");

        LocalDateTime time = LocalDateTime.of(2023, 4, 11, 16, 29, 14);

        assertEquals("TX000001", transaction.transactionID(), "Transaction ID is set incorrect");
        assertEquals("AC00128", transaction.accountID(), "Account ID is set incorrect");
        assertEquals(14.09, transaction.transactionAmount(), "Transaction amount is set incorrect");
        assertEquals(time, transaction.transactionDate(), "Transaction date is set incorrect");
        assertEquals("San Diego", transaction.location(), "Transaction ID is set incorrect");
        assertEquals(Channel.BRANCH, transaction.channel(), "Branch channel is set incorrect");
    }

    @Test
    public void testTransactionWithInvalidChanel() {
        assertThrows(IllegalArgumentException.class,
            () -> Transaction.of("TX000001,AC00128,14.09,2023-04-11 16:29:14,San Diego,ASD"), "Channel is invalid");
    }

}
