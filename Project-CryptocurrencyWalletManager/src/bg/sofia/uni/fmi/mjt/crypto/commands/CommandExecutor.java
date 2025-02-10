package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.LoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;

import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class CommandExecutor {

    private static final String SPLIT_SYMBOL = " ";
    private static final String REPLACEMENT_SYMBOL = "";

    private static final int COMMAND_INDEX = 0;
    private static final int SKIP_ELEMENTS = 1;

    private final CommandFactory commandFactory;

    public CommandExecutor(Arguments arguments) {
        this.commandFactory = new CommandFactory(arguments);
    }

    public String execute(String line, SocketChannel socketChannel)
        throws UserExistsException, InvalidAmountException, UserDoesNotExistsException,
        InvalidCommandException, InvalidCountOfArgumentsException, InsufficientFundsException, CryptoNotFoundException,
        NotLoginException, LoginException {
        if (line == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_LINE);
        }
        line = line.replaceAll(System.lineSeparator(), REPLACEMENT_SYMBOL);
        line = line.trim();
        String[] args = line.split(SPLIT_SYMBOL);
        String command = args[COMMAND_INDEX];

        args = Arrays.stream(args)
            .skip(SKIP_ELEMENTS)
            .filter(s -> !s.isBlank())
            .toArray(String[]::new);

        return commandFactory.getCommand(command, socketChannel).execute(args);
    }

}
