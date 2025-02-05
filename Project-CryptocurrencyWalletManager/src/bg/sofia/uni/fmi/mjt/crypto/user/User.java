package bg.sofia.uni.fmi.mjt.crypto.user;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountException;
import bg.sofia.uni.fmi.mjt.crypto.server.hasher.PasswordHasher;
import bg.sofia.uni.fmi.mjt.crypto.wallet.Wallet;

import java.util.Map;
import java.util.Objects;

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

    public String depositMoney(double amount) throws InvalidAmountException {
        wallet.depositMoney(amount);
        return "The deposit is successful!";
    }

    public String buyCrypto(String assetId, double amount,  double price)
        throws InvalidAmountException, InsufficientFundsException, CryptoNotFoundException {
        wallet.buyCrypto(assetId, amount, price);
        return "The purchase is successful!";
    }

    public String sellCrypto(String assetId, double currentPrice) throws CryptoNotFoundException,
        InvalidAmountException {
        wallet.sell(assetId, currentPrice);
        return "The sale is successful!";
    }

    public String getWalletSummary() {
        return String.format("Wallet summary: %s", wallet.getWalletSummary());
    }

    public String getWalletOverallSummary(Map<String, Double> currentPrices) throws CryptoNotFoundException {
        return String.format("Wallet overall summary: %s", wallet.getWalletOverallSummary(currentPrices));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
            PasswordHasher.verifyPassword(hashedPassword, user.hashedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, hashedPassword);
    }

}
