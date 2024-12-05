package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class LocationsRule implements Rule {

    private final int threshold;
    private final double weight;

    public LocationsRule(int threshold, double weight) {
        if (threshold < 1) {
            throw new IllegalArgumentException("Threshold must be greater than 0");
        }
        if (weight < 0.0 || weight > 1.0) {
            throw new IllegalArgumentException("Weight must be between 0.0 and 1.0");
        }

        this.threshold = threshold;
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
        if (transactions.size() < threshold) {
            return false;
        }

        long countDiffLocations = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::location))
            .size();

        return countDiffLocations >= threshold;
    }

    @Override
    public double weight() {
        return weight;
    }

}
