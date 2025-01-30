package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;

import java.nio.channels.SocketChannel;

public class GetWalletSummaryCommand implements Command {

    private final UserRepository userRepository;
    private final SocketChannel socketChannel;

    public GetWalletSummaryCommand(UserRepository userRepository, SocketChannel socketChannel) {
        this.userRepository = userRepository;
        this.socketChannel = socketChannel;
    }

    @Override
    public String execute(String[] args) throws InvalidCountOfArgumentsException {
        if (args.length != 0) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        String username = UserSessionManager.getUsername(socketChannel);

        return userRepository.getUser(username).getWalletSummary();
    }

}
