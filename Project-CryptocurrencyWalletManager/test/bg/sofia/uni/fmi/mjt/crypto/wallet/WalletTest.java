package bg.sofia.uni.fmi.mjt.crypto.wallet;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WalletTest {

    private Wallet wallet;

    @BeforeEach
    public void setUp() {
        wallet = new Wallet();
    }

    @Test
    public void testDepositMoneyInvalidAmount() {
        double deposit = 0;
        assertThrows(InvalidAmountException.class, () -> wallet.depositMoney(deposit),
            "When deposit is amount is less or equal to zero InvalidAmountException should be thrown");
    }

    @Test
    public void testDepositMoneyValidAmount() throws InvalidAmountException {
        double deposit = 1000;
        wallet.depositMoney(deposit);

        assertEquals(deposit, wallet.getBalance(),
            "Deposit money does not work correctly");
    }

    @Test
    public void testBuyCryptoWithNullAssetId() {
        assertThrows(CryptoNotFoundException.class, () -> wallet.buyCrypto(null, 100, 1000),
            "When assetID is null CryptoNotFoundException should be thrown");
    }

    @Test
    public void testBuyCryptoWithInvalidCurrentPrice() {
        assertThrows(InvalidAmountException.class, () -> wallet.buyCrypto("BTC", 10, 0),
            "When currentPrice is less or equal to zero InvalidAmountException should be thrown");
    }

    @Test
    public void testBuyCryptoWithInvalidAmount() {
        assertThrows(InvalidAmountException.class, () -> wallet.buyCrypto("BTC", 0, 1000),
            "When amount for buying is less or equals to zero InvalidAmountException should be thrown");
    }

    @Test
    public void testBuyCryptoWithInsufficientFunds() {
        assertThrows(InsufficientFundsException.class, () -> wallet.buyCrypto("BTC", 10, 1000),
            "When balance is less than amount of buying InsufficientFundsException should be thrown");
    }

    @Test
    public void testBuyCrypto() throws InvalidAmountException, InsufficientFundsException, CryptoNotFoundException {
        wallet.depositMoney(1000);

        wallet.buyCrypto("BTC", 100.10, 100_000);
        wallet.buyCrypto("BTC", 100.10, 100_000.1000);

        assertEquals(wallet.getCryptoInvestments().get("BTC").size(), 2,
            "When buyCrypto is called for the same crypto twice the buy records must be 2");
    }

    @Test
    public void testSellWithNullAssetId() {
        assertThrows(CryptoNotFoundException.class, () -> wallet.sell(null, 1000),
            "When assetID is null CryptoNotFoundException should be thrown");
    }

    @Test
    public void testSellWithInvalidCurrentPrice() {
        assertThrows(InvalidAmountException.class, () -> wallet.sell("BTC", 0),
            "When currentPrice is less or equal to zero InvalidAmountException should be thrown");
    }

    @Test
    public void testSellWithUnavailableAssetId() {
        assertThrows(CryptoNotFoundException.class, () -> wallet.sell("BTC", 1000),
            "When assetId doesn't exist in the wallet CryptoNotFoundException should be thrown");
    }

    @Test
    public void testSell() throws InvalidAmountException, InsufficientFundsException, CryptoNotFoundException {
        wallet.depositMoney(1000);

        wallet.buyCrypto("BTC", 100, 100_000);
        wallet.buyCrypto("BTC", 200, 50_000);

        wallet.sell("BTC", 200_000);
        assertEquals(1700, wallet.getBalance(),
            "When sell crypto balance should be updated with all sales");
    }

    @Test
    public void testGetWalletSummary() {
        String summary = wallet.getWalletSummary();
        assertTrue(summary.contains("balance"), "GetWalletSummary return incorrect format for balance");
        assertTrue(summary.contains("cryptoInvestments"),
            "GetWalletSummary return incorrect format for cryptoInvestments");
    }

    @Test
    public void testGetWalletOverallSummaryWithNullPrices() {
        assertThrows(CryptoNotFoundException.class, () -> wallet.getWalletOverallSummary(null),
            "When cryptoPrices are null CryptoNotFoundException should be thrown");
    }

    @Test
    public void testGetWalletOverallSummaryWithNoInvestments() throws CryptoNotFoundException {
        Map<String, Double> prices = Map.of("BTC", 123.10);
        assertEquals("No investments found.", wallet.getWalletOverallSummary(prices));
    }

    @Test
    public void testGetWalletOverallSummaryWithPricesThatDidNotContainAllAvailableCryptos()
        throws InvalidAmountException, InsufficientFundsException, CryptoNotFoundException {
        Map<String, Double> prices = Map.of("BTC", 123.10);
        wallet.depositMoney(1000);
        wallet.buyCrypto("ETH", 100, 100);
        assertThrows(CryptoNotFoundException.class, () -> wallet.getWalletOverallSummary(prices),
            "No price for crypto in availableCryptos");
    }

    @Test
    public void testGetWalletOverallSummary()
        throws InvalidAmountException, InsufficientFundsException, CryptoNotFoundException {
        Map<String, Double> prices = Map.of("BTC", 123.10, "ETH", 10000.0);
        wallet.depositMoney(1000);
        wallet.buyCrypto("ETH", 100, 100);

        String response = wallet.getWalletOverallSummary(prices);

        assertTrue(response.contains("invested_amount"),
            "GetWalletOverallSummary return incorrect format for invested_amount");
        assertTrue(response.contains("current_amount"),
            "GetWalletOverallSummary return incorrect format for current_amount");
        assertTrue(response.contains("difference"),
            "GetWalletOverallSummary return incorrect format for difference");
    }

}
