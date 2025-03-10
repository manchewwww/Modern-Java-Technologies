package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TransactionAnalyzerImpl implements TransactionAnalyzer {

    private final List<Transaction> transactions;
    private final List<Rule> rules;

    public TransactionAnalyzerImpl(Reader reader, List<Rule> rules) {
        if (reader == null) {
            throw new IllegalArgumentException("reader is null");
        }
        if (rules == null) {
            throw new IllegalArgumentException("rules is null");
        }

        double rulesRisk = rules.stream().mapToDouble(Rule::weight).sum();
        if (rulesRisk != 1) {
            throw new IllegalArgumentException("rulesRisk is " + rulesRisk);
        }
        this.rules = rules;

        var input = new BufferedReader(reader);
        transactions = input.lines().skip(1).map(Transaction::of).toList();
    }

    private void validateAccountID(String accountID) {
        if (accountID == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
        if (accountID.isEmpty() || accountID.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be empty");
        }
    }

    @Override
    public List<Transaction> allTransactions() {
        return transactions;
    }

    @Override
    public List<String> allAccountIDs() {
        return transactions.stream()
            .map(Transaction::accountID)
            .distinct()
            .toList();
    }

    @Override
    public Map<Channel, Integer> transactionCountByChannel() {
        return transactions.stream()
            .collect(Collectors.groupingBy(Transaction::channel, Collectors.summingInt(t -> 1)));
    }

    @Override
    public double amountSpentByUser(String accountID) {
        validateAccountID(accountID);

        return transactions.stream()
            .filter(transaction -> transaction.accountID().equals(accountID))
            .mapToDouble(Transaction::transactionAmount)
            .sum();
    }

    @Override
    public List<Transaction> allTransactionsByUser(String accountId) {
        validateAccountID(accountId);

        return transactions.stream()
            .filter(transaction -> transaction.accountID().equals(accountId))
            .toList();
    }

    @Override
    public double accountRating(String accountId) {
        List<Transaction> transactionsFromUser = allTransactionsByUser(accountId);

        if (transactionsFromUser.isEmpty()) {
            return 0.0;
        }

        return rules.stream()
            .filter(rule -> rule.applicable(transactionsFromUser))
            .mapToDouble(Rule::weight)
            .sum();
    }

    @Override
    public SortedMap<String, Double> accountsRisk() {
        Map<String, Double> accountsRisks =
            allAccountIDs().stream()
                .collect(Collectors.toMap(accountID -> accountID, this::accountRating));

        TreeMap<String, Double> sortedMap = new TreeMap<>((o1, o2) -> {
            int compareRisk = accountsRisks.get(o2).compareTo(accountsRisks.get(o1));
            if (compareRisk != 0) {
                return compareRisk;
            }
            return o1.compareTo(o2);
        });
        sortedMap.putAll(accountsRisks);
        return sortedMap;
    }

}
