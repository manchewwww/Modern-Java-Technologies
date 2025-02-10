package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;

import java.nio.channels.SocketChannel;

public class GetWalletOverallSummaryCommand implements Command {

    private static final int ARGS_LENGTH = 0;

    private final Arguments arguments;
    private final SocketChannel socketChannel;

    public GetWalletOverallSummaryCommand(Arguments arguments, SocketChannel socketChannel) {
        this.arguments = arguments;
        this.socketChannel = socketChannel;
    }

    @Override
    public String execute(String[] args)
        throws InvalidCountOfArgumentsException, CryptoNotFoundException, NotLoginException,
        UserDoesNotExistsException {
        if (args.length != ARGS_LENGTH) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        String username = arguments.getUserSessionManager().getUsername(socketChannel);

        return arguments.getUserRepository().getUser(username)
            .getWalletOverallSummary(arguments.getDataRepository().getCacheData().getPrices());
    }

}
