package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountOfDepositException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserExistsException;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.DataRepository;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;

import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class CommandExecutor {

    private final CommandFactory commandFactory;

    public CommandExecutor(UserRepository userRepository, DataRepository dataRepository) {
        this.commandFactory = new CommandFactory(userRepository, dataRepository);
    }

    public String execute(String line, SocketChannel socketChannel)
        throws UserExistsException, InvalidAmountOfDepositException, UserDoesNotExistsException,
        InvalidCommandException, InvalidCountOfArgumentsException, InsufficientFundsException, CryptoNotFoundException {
        line = line.replaceAll(System.lineSeparator(), "");
        String[] args = line.split(" ");
        if (args.length == 0) {
            return "\"{\"status\":\"ERROR\",\"message\":\"Invalid command\"}\"";
        }
        String command = args[0];
        args = Arrays.stream(args)
            .skip(1)
            .filter(s -> !s.isBlank())
            .toArray(String[]::new);

        return commandFactory.getCommand(command, socketChannel).execute(args);
    }

}
