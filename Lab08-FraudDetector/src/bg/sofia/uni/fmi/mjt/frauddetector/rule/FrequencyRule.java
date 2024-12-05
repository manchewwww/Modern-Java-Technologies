package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Comparator;
import java.util.List;

public class FrequencyRule implements Rule {

    private final int transactionCountThreshold;
    private final TemporalAmount timeWindow;
    private final double weight;

    public FrequencyRule(int transactionCountThreshold, TemporalAmount timeWindow, double weight) {
        if (transactionCountThreshold < 1) {
            throw new IllegalArgumentException("transactionCountThreshold must be greater than 0");
        }
        if (timeWindow == null) {
            throw new IllegalArgumentException("timeWindow cannot be null");
        }
        if (weight < 0.0 || weight > 1.0) {
            throw new IllegalArgumentException("weight must be between 0.0 and 1.0");
        }

        this.transactionCountThreshold = transactionCountThreshold;
        this.timeWindow = timeWindow;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        if (transactions == null) {
            throw new IllegalArgumentException("Transactions must not be null");
        }
        if (transactions.isEmpty()) {
            return false;
        }
        if (transactions.size() < transactionCountThreshold) {
            return false;
        }

        for (Transaction transaction : transactions) {
            LocalDateTime windowStart = transaction.transactionDate();
            LocalDateTime windowEnd = windowStart.plus(timeWindow);

            long count =
                transactions.stream()
                    .filter(trans -> !(trans.transactionDate().isAfter(windowEnd) ||
                        trans.transactionDate().isBefore(windowStart)))
                    .count();

            if (count >= transactionCountThreshold) {
                return true;
            }
        }

        return false;
    }

    @Override
    public double weight() {
        return weight;
    }

}
