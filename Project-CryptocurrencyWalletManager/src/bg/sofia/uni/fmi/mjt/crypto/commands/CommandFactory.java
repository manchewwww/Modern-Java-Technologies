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
        CommandType commandType = CommandType.getCommandType(command);
        return switch (commandType) {
            case HELP-> new HelpCommand();
            case REGISTER -> new RegisterCommand(arguments);
            case LOGIN -> new LoginCommand(arguments, channel);
            case LOGOUT -> new LogoutCommand(arguments, channel);
            case DEPOSIT -> new DepositMoneyCommand(arguments, channel);
            case BUY -> new BuyCommand(arguments, channel);
            case SELL -> new SellCommand(arguments, channel);
            case LIST -> new ListOfferingsCommand(arguments);
            case WALLET_SUMMARY -> new GetWalletSummaryCommand(arguments, channel);
            case WALLET_OVERALL_SUMMARY -> new GetWalletOverallSummaryCommand(arguments, channel);
            case INVALID -> throw new InvalidCommandException("Invalid command: " + command);
        };
    }

}
