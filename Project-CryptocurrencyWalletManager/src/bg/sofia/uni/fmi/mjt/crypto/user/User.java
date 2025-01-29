package bg.sofia.uni.fmi.mjt.crypto.user;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountOfDepositException;
import bg.sofia.uni.fmi.mjt.crypto.wallet.Wallet;

import java.util.Map;

public class User {

    private final String username;
    private final String hashedPassword;
    private final Wallet wallet;

    public User(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.wallet = new Wallet();
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String depositMoney(double amount) throws InvalidAmountOfDepositException {
        wallet.depositMoney(amount);
        return "The deposit is successful!";
    }

    public String buyCrypto(String assetId, double amount,  double price)
        throws InvalidAmountOfDepositException, InsufficientFundsException {
        wallet.buyCrypto(assetId, price , amount);
        return "The purchase is successful!";
    }

    public String sellCrypto(String assetId, double currentPrice) throws CryptoNotFoundException {
        wallet.sell(assetId, currentPrice);
        return "The sale is successful!";
    }

    public String getWalletSummary() {
        return String.format("Wallet summary: %s", wallet.getWalletSummary());
    }

    public String getWalletOverallSummary(Map<String, Double> currentPrices) throws CryptoNotFoundException {
        return String.format("Wallet overall summary: %s", wallet.getWalletOverallSummary(currentPrices));
    }

}
