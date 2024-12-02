package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class TransactionAnalyzerImpl implements TransactionAnalyzer {

    public TransactionAnalyzerImpl(Reader reader, List<Rule> rules) {

    }

    @Override
    public List<Transaction> allTransactions() {
        return List.of();
    }

    @Override
    public List<String> allAccountIDs() {
        return List.of();
    }

    @Override
    public Map<Channel, Integer> transactionCountByChannel() {
        return Map.of();
    }

    @Override
    public double amountSpentByUser(String accountID) {
        return 0;
    }

    @Override
    public List<Transaction> allTransactionsByUser(String accountId) {
        return List.of();
    }

    @Override
    public double accountRating(String accountId) {
        return 0;
    }

    @Override
    public SortedMap<String, Double> accountsRisk() {
        return null;
    }

}
