package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCommandException;

import java.nio.channels.SocketChannel;

public class CommandFactory {

    private final Arguments arguments;

    public CommandFactory(Arguments arguments) {
        this.arguments = arguments;
    }

    public Command getCommand(String command, SocketChannel channel) throws InvalidCommandException {
        return switch (command) {
            case "help" -> new HelpCommand();
            case "register" -> new RegisterCommand(arguments);
            case "login" -> new LoginCommand(arguments, channel);
            case "logout" -> new LogoutCommand(arguments, channel);
            case "deposit-money" -> new DepositMoneyCommand(arguments, channel);
            case "buy" -> new BuyCommand(arguments, channel);
            case "sell" -> new SellCommand(arguments, channel);
            case "list-offerings" -> new ListOfferingsCommand(arguments);
            case "get-wallet-summary" -> new GetWalletSummaryCommand(arguments, channel);
            case "get-wallet-overall-summary" -> new GetWalletOverallSummaryCommand(arguments, channel);
            default -> throw new InvalidCommandException("Invalid command: " + command);
        };
    }

}
