package bg.sofia.uni.fmi.mjt.crypto.wallet;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Wallet {

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

    public void depositMoney(double amount) throws InvalidAmountException {
        checkAmountOfMoney(amount);

        synchronized (this) {
            this.balance += amount;
        }
    }

    public void buyCrypto(String assetId, double amount,  double currentPrice)
        throws InvalidAmountException, InsufficientFundsException, CryptoNotFoundException {
        checkAssetID(assetId);
        checkCurrentPrice(currentPrice);
        checkAmountOfMoney(amount);

        synchronized (this) {
            if (amount > this.balance) {
                throw new InsufficientFundsException(ErrorMessages.INSUFFICIENT_BALANCE_MESSAGE);
            }
            this.balance -= amount;

            cryptoInvestments.putIfAbsent(assetId, new ArrayList<>());
            cryptoInvestments.get(assetId)
                .add(new Crypto(assetId, currentPrice, TYPE_CRYPTO, amount / currentPrice));
        }
    }

    public void sell(String assetId, double currentPrice) throws CryptoNotFoundException, InvalidAmountException {
        checkAssetID(assetId);
        checkCurrentPrice(currentPrice);

        synchronized (this) {
            if (!cryptoInvestments.containsKey(assetId)) {
                throw new CryptoNotFoundException(ErrorMessages.CRYPTO_DOES_NOT_EXIST_MESSAGE);
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
            throw new CryptoNotFoundException(ErrorMessages.CURRENT_PRICES_NULL_MESSAGE);
        }
        if (cryptoInvestments.isEmpty()) {
            return ErrorMessages.NO_INVESTMENTS_FOUND_MESSAGE;
        }
        if (!currentPrices.keySet().containsAll(cryptoInvestments.keySet())) {
            throw new CryptoNotFoundException(ErrorMessages.CRYPTO_DOES_NOT_EXIST_MESSAGE);
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

    private void checkAmountOfMoney(double amount) throws InvalidAmountException {
        if (amount <= MINIMUM_AMOUNT) {
            throw new InvalidAmountException(ErrorMessages.INVALID_DEPOSIT_AMOUNT_MESSAGE);
        }
    }

    private void checkAssetID(String assetId) throws CryptoNotFoundException {
        if (assetId == null) {
            throw new CryptoNotFoundException(ErrorMessages.CRYPTO_ASSETS_ID_NULL_MESSAGE);
        }
    }

    private void checkCurrentPrice(double currentPrice) throws InvalidAmountException {
        if (currentPrice <= MINIMUM_AMOUNT) {
            throw new InvalidAmountException(ErrorMessages.INVALID_CURRENT_PRICE_MESSAGE);
        }
    }

}
