package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.crypto.server.api.CoinApi;
import bg.sofia.uni.fmi.mjt.crypto.server.api.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;

import java.nio.channels.SocketChannel;

public class CommandFactory {

    private final UserRepository userRepository;
    private final CacheData cacheData;

    public CommandFactory(UserRepository userRepository, CacheData cacheData) {
        this.userRepository = userRepository;
        this.cacheData = cacheData;
    }

    public Command getCommand(String command, SocketChannel channel) throws InvalidCommandException {
        return switch (command) {
            case "help" -> new HelpCommand();
            case "register" -> new RegisterCommand(userRepository);
            case "login" -> new LoginCommand(userRepository, channel);
            case "logout" -> new LogOutCommand(channel);
            case "deposit-money" -> new DepositMoneyCommand(userRepository, channel);
            case "buy" -> new BuyCommand(userRepository, channel, cacheData);
            case "sell" -> new SellCommand(userRepository, channel, cacheData);
            case "list-offerings" -> new ListOfferingsCommand(cacheData);
            case "get-wallet-summary" -> new GetWalletSummaryCommand(userRepository, channel);
            case "get-wallet-overall-summary" -> new GetWalletOverallSummaryCommand(userRepository, channel, cacheData);
            default -> throw new InvalidCommandException("Invalid command: " + command);
        };
    }

}
