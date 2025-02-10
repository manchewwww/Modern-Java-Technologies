package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;

import java.nio.channels.SocketChannel;

public class DepositMoneyCommand implements Command {

    private static final int ARGS_LENGTH = 1;
    private static final int AMOUNT_INDEX = 0;

    private final Arguments arguments;
    private final SocketChannel socketChannel;

    public DepositMoneyCommand(Arguments arguments, SocketChannel socketChannel) {
        this.arguments = arguments;
        this.socketChannel = socketChannel;
    }

    @Override
    public String execute(String[] args)
        throws InvalidAmountException, InvalidCountOfArgumentsException, NotLoginException, UserDoesNotExistsException {
        if (args.length != ARGS_LENGTH) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        double amount = Double.parseDouble(args[AMOUNT_INDEX]);
        String username = arguments.getUserSessionManager().getUsername(socketChannel);

        return arguments.getUserRepository().getUser(username).depositMoney(amount);
    }

}
