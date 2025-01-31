package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.DataRepository;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;

import java.nio.channels.SocketChannel;

public class CommandFactory {

    private final UserRepository userRepository;
    private final DataRepository dataRepository;

    public CommandFactory(UserRepository userRepository, DataRepository dataRepository) {
        this.userRepository = userRepository;
        this.dataRepository = dataRepository;
    }

    public Command getCommand(String command, SocketChannel channel) throws InvalidCommandException {
        return switch (command) {
            case "help" -> new HelpCommand();
            case "register" -> new RegisterCommand(userRepository);
            case "login" -> new LoginCommand(userRepository, channel);
            case "logout" -> new LogOutCommand(channel);
            case "deposit-money" -> new DepositMoneyCommand(userRepository, channel);
            case "buy" -> new BuyCommand(userRepository, channel, dataRepository.getCacheData());
            case "sell" -> new SellCommand(userRepository, channel, dataRepository.getCacheData());
            case "list-offerings" -> new ListOfferingsCommand(dataRepository.getCacheData());
            case "get-wallet-summary" -> new GetWalletSummaryCommand(userRepository, channel);
            case "get-wallet-overall-summary" ->
                new GetWalletOverallSummaryCommand(userRepository, channel, dataRepository.getCacheData());
            default -> throw new InvalidCommandException("Invalid command: " + command);
        };
    }

}
