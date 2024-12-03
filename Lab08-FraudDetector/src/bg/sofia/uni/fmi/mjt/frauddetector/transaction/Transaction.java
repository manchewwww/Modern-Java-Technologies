package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Transaction(String transactionID, String accountID, double transactionAmount,
                          LocalDateTime transactionDate, String location, Channel channel) {

    public static Transaction of(String line) {
        final String[] tokens = line.split(",");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        final int indexOFTransactionID = 0;
        final int indexOFAccountID = 1;
        final int indexOFTransactionAmount = 2;
        final int indexOFTransactionDate = 3;
        final int indexOFLocation = 4;
        final int indexOFChannel = 5;

        LocalDateTime transactionDate = LocalDateTime.parse(tokens[indexOFTransactionDate], dateTimeFormatter);

        Channel chanel = switch (tokens[indexOFChannel]) {
            case "ATM" -> Channel.ATM;
            case "Online" -> Channel.ONLINE;
            case "Branch" -> Channel.BRANCH;
            default -> throw new IllegalArgumentException("Unknown channel: " + tokens[indexOFChannel]);
        };

        return new Transaction(tokens[indexOFTransactionID], tokens[indexOFAccountID],
            Double.parseDouble(tokens[indexOFTransactionAmount]), transactionDate, tokens[indexOFLocation], chanel);
    }

}
