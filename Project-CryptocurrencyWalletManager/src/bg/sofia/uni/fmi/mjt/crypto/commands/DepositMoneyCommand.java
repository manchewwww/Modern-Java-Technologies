package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountOfDepositException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;

import java.nio.channels.SocketChannel;

public class DepositMoneyCommand implements Command {

    private final UserRepository userRepository;
    private final SocketChannel socketChannel;

    public DepositMoneyCommand(UserRepository userRepository, SocketChannel socketChannel) {
        this.userRepository = userRepository;
        this.socketChannel = socketChannel;
    }

    @Override
    public String execute(String[] args)
        throws UserDoesNotExistsException, InvalidAmountOfDepositException, InvalidCountOfArgumentsException {
        if (args.length != 1) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        double amount = Double.parseDouble(args[0]);
        String username = UserSessionManager.getUsername(socketChannel);
        return userRepository.getUser(username).depositMoney(amount);
    }

}
