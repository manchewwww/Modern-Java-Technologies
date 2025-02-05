package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;

import java.nio.channels.SocketChannel;

public class BuyCommand implements Command {

    private final UserRepository userRepository;
    private final SocketChannel socketChannel;
    private final CacheData cacheData;
    private final UserSessionManager userSessionManager;

    public BuyCommand(UserRepository userRepository, UserSessionManager userSessionManager, SocketChannel socketChannel,
                      CacheData cacheData) {
        this.userRepository = userRepository;
        this.socketChannel = socketChannel;
        this.cacheData = cacheData;
        this.userSessionManager = userSessionManager;
    }

    @Override
    public String execute(String[] args)
        throws InvalidCountOfArgumentsException, InvalidAmountException,
        InsufficientFundsException, CryptoNotFoundException, NotLoginException, UserDoesNotExistsException {
        if (args.length != 2) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        String assetId = args[0];
        double amount = Double.parseDouble(args[1]);

        String username = userSessionManager.getUsername(socketChannel);

        return userRepository.getUser(username).buyCrypto(assetId, amount, cacheData.getPriceFromAssetId(assetId));
    }

}
