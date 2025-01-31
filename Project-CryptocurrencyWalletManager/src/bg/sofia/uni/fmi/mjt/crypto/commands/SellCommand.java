package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;

import java.nio.channels.SocketChannel;

public class SellCommand implements Command {

    private final UserRepository userRepository;
    private final SocketChannel socketChannel;
    private final CacheData cacheData;

    public SellCommand(UserRepository userRepository, SocketChannel socketChannel, CacheData cacheData) {
        this.userRepository = userRepository;
        this.socketChannel = socketChannel;
        this.cacheData = cacheData;
    }

    @Override
    public String execute(String[] args)
        throws InvalidCountOfArgumentsException, CryptoNotFoundException {
        if (args.length != 1) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        String assetId = args[0];
        String username = UserSessionManager.getUsername(socketChannel);

        return userRepository.getUser(username).sellCrypto(assetId, cacheData.getPriceFromAssetId(assetId));
    }

}
