package bg.sofia.uni.fmi.mjt.crypto.wallet;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountOfDepositException;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Wallet {

    private static final String INVALID_DEPOSIT_AMOUNT_MESSAGE = "Deposit amount must be greater than 0.0";
    private static final String INVALID_CURRENT_PRICE_MESSAGE = "Current price must be greater than zero.";
    private static final String CRYPTO_ASSETS_ID_NULL_MESSAGE = "Crypto asset id cannot be null.";
    private static final String INSUFFICIENT_BALANCE_MESSAGE = "You don't have enough money.";
    private static final String CRYPTO_DOES_NOT_EXIST_MESSAGE = "Crypto asset id does not exist.";
    private static final String CURRENT_PRICES_NULL_MESSAGE = "currentPrices cannot be null";
    private static final String NO_INVESTMENTS_FOUND_MESSAGE = "No investments found.";

    private static final double MINIMUM_AMOUNT = 0.0;
    private static final int TYPE_CRYPTO = 1;
    private static final Gson GSON = new Gson();

    private double balance;
    private final Map<String, List<Crypto>> cryptoInvestments;

    public Wallet() {
        this.balance = MINIMUM_AMOUNT;
        this.cryptoInvestments = new ConcurrentHashMap<>();
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, List<Crypto>> getCryptoInvestments() {
        return cryptoInvestments;
    }

    public void depositMoney(double amount) throws InvalidAmountOfDepositException {
        if (amount <= MINIMUM_AMOUNT) {
            throw new InvalidAmountOfDepositException(INVALID_DEPOSIT_AMOUNT_MESSAGE);
        }

        synchronized (this) {
            this.balance += amount;
        }
    }

    public void buyCrypto(String assetId, double currentPrice, double amount)
        throws InvalidAmountOfDepositException, InsufficientFundsException {
        if (assetId == null) {
            throw new IllegalArgumentException(CRYPTO_ASSETS_ID_NULL_MESSAGE);
        }
        if (currentPrice <= MINIMUM_AMOUNT) {
            throw new IllegalArgumentException(INVALID_CURRENT_PRICE_MESSAGE);
        }
        if (amount <= MINIMUM_AMOUNT) {
            throw new InvalidAmountOfDepositException(INVALID_DEPOSIT_AMOUNT_MESSAGE);
        }

        synchronized (this) {
            if (amount > this.balance) {
                throw new InsufficientFundsException(INSUFFICIENT_BALANCE_MESSAGE);
            }
            this.balance -= amount;

            cryptoInvestments.putIfAbsent(assetId, new ArrayList<>());
            cryptoInvestments.get(assetId)
                .add(new Crypto(assetId, currentPrice, TYPE_CRYPTO, amount / currentPrice));
        }
    }

    public void sell(String assetId, double currentPrice) throws CryptoNotFoundException {
        if (assetId == null) {
            throw new IllegalArgumentException(CRYPTO_ASSETS_ID_NULL_MESSAGE);
        }
        if (currentPrice <= MINIMUM_AMOUNT) {
            throw new IllegalArgumentException(INVALID_CURRENT_PRICE_MESSAGE);
        }

        synchronized (this) {
            if (!cryptoInvestments.containsKey(assetId)) {
                throw new CryptoNotFoundException(CRYPTO_DOES_NOT_EXIST_MESSAGE);
            }

            double allCryptoWithAssetId = cryptoInvestments.get(assetId).stream()
                .mapToDouble(Crypto::quantity)
                .sum();

            cryptoInvestments.remove(assetId);

            balance += allCryptoWithAssetId * currentPrice;
        }
    }

    public synchronized String getWalletSummary() {
        return GSON.toJson(this);
    }

    public synchronized String getWalletOverallSummary(Map<String, Double> currentPrices)
        throws CryptoNotFoundException {
        if (currentPrices == null) {
            throw new IllegalArgumentException(CURRENT_PRICES_NULL_MESSAGE);
        }
        if (cryptoInvestments.isEmpty()) {
            return NO_INVESTMENTS_FOUND_MESSAGE;
        }
        if (!currentPrices.keySet().containsAll(cryptoInvestments.keySet())) {
            throw new CryptoNotFoundException(CRYPTO_DOES_NOT_EXIST_MESSAGE);
        }

        double investedAmountInCrypto = cryptoInvestments.values().stream()
            .mapToDouble(cryptos -> cryptos.stream()
                .mapToDouble(
                    crypto -> crypto.quantity() * crypto.priceUsd())
                .sum())
            .sum();

        double currentAmountInCrypto = cryptoInvestments.values().stream()
            .mapToDouble(cryptos -> cryptos.stream()
                .mapToDouble(
                    crypto -> crypto.quantity() * currentPrices.get(crypto.assetId()))
                .sum())
            .sum();

        WalletSummary walletSummary = new WalletSummary(investedAmountInCrypto, currentAmountInCrypto,
            currentAmountInCrypto - investedAmountInCrypto);

        return GSON.toJson(walletSummary);
    }

}
