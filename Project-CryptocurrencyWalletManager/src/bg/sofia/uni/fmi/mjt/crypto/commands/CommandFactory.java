package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.DataRepository;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;

import java.nio.channels.SocketChannel;

public class CommandFactory {

    private final UserRepository userRepository;
    private final DataRepository dataRepository;
    private final UserSessionManager userSessionManager;

    public CommandFactory(UserRepository userRepository, DataRepository dataRepository,
                          UserSessionManager userSessionManager) {
        this.userRepository = userRepository;
        this.dataRepository = dataRepository;
        this.userSessionManager = userSessionManager;
    }

    public Command getCommand(String command, SocketChannel channel) throws InvalidCommandException {
        return switch (command) {
            case "help" -> new HelpCommand();
            case "register" -> new RegisterCommand(userRepository);
            case "login" -> new LoginCommand(userRepository, userSessionManager, channel);
            case "logout" -> new LogoutCommand(userSessionManager, channel);
            case "deposit-money" -> new DepositMoneyCommand(userRepository, userSessionManager, channel);
            case "buy" -> new BuyCommand(userRepository, userSessionManager, channel, dataRepository.getCacheData());
            case "sell" -> new SellCommand(userRepository, userSessionManager, channel, dataRepository.getCacheData());
            case "list-offerings" -> new ListOfferingsCommand(dataRepository.getCacheData());
            case "get-wallet-summary" -> new GetWalletSummaryCommand(userRepository, userSessionManager, channel);
            case "get-wallet-overall-summary" ->
                new GetWalletOverallSummaryCommand(userRepository, userSessionManager, channel,
                    dataRepository.getCacheData());
            default -> throw new InvalidCommandException("Invalid command: " + command);
        };
    }

}
