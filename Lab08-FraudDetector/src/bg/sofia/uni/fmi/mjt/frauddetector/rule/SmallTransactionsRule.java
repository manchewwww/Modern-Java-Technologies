package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class SmallTransactionsRule implements Rule {

    private final int countThreshold;
    private final double amountThreshold;
    private final double weight;

    public SmallTransactionsRule(int countThreshold, double amountThreshold, double weight) {
        if (countThreshold < 1) {
            throw new IllegalArgumentException("Count threshold must be greater than 0");
        }
        if (amountThreshold < 0) {
            throw new IllegalArgumentException("Amount threshold must be non-negative");
        }
        if (weight < 0.0 || weight > 1.0) {
            throw new IllegalArgumentException("Weight must be between 0.0 and 1.0");
        }

        this.countThreshold = countThreshold;
        this.amountThreshold = amountThreshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return false;
        }
        if (transactions.size() < countThreshold) {
            return false;
        }

        long countSmallTransactions =
            transactions.stream()
                .filter(transaction -> transaction.transactionAmount() <= amountThreshold)
                .count();

        return countSmallTransactions >= countThreshold;
    }

    @Override
    public double weight() {
        return weight;
    }

}
