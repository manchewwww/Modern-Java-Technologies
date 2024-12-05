package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class ZScoreRule implements Rule {

    private final double zScoreThreshold;
    private final double weight;

    public ZScoreRule(double zScoreThreshold, double weight) {
        if (zScoreThreshold < 0) {
            throw new IllegalArgumentException("Z-score threshold must be non-negative");
        }
        if (weight < 0.0 || weight > 1.0) {
            throw new IllegalArgumentException("Weight must be between 0.0 and 1.0");
        }

        this.zScoreThreshold = zScoreThreshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        if (transactions == null) {
            throw new IllegalArgumentException("Transactions cannot be null");
        }
        if (transactions.isEmpty()) {
            return false;
        }

        double averageAmount = transactions.stream()
                .mapToDouble(Transaction::transactionAmount)
                .average().orElse(0);

        double variance = transactions.stream()
                .mapToDouble(Transaction::transactionAmount)
                .map(amount -> Math.pow(amount - averageAmount, 2))
                .average()
                .orElse(0);

        double standardDeviation = Math.sqrt(variance);
        if (standardDeviation == 0) {
            return false;
        }

        long zScores = transactions.stream()
                .mapToDouble(Transaction::transactionAmount)
                .map(amount -> (amount - averageAmount) / standardDeviation)
                .filter(zScore ->  zScore > zScoreThreshold)
                .count();
        return zScores > 0;
    }

    @Override
    public double weight() {
        return weight;
    }

}
