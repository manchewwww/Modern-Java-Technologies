package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.LoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;

import java.nio.channels.SocketChannel;

public class LoginCommand implements Command {

    private static final int ARGS_LENGTH = 2;
    private static final int USERNAME_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    private static final String INCORRECT_PASSWORD = "Incorrect password";

    private final SocketChannel socketChannel;
    private final Arguments arguments;

    public LoginCommand(Arguments arguments, SocketChannel socketChannel) {
        this.arguments = arguments;
        this.socketChannel = socketChannel;
    }

    @Override
    public String execute(String[] args)
        throws UserDoesNotExistsException, InvalidCountOfArgumentsException, LoginException {
        if (args.length != ARGS_LENGTH) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        String username = args[USERNAME_INDEX];
        String password = args[PASSWORD_INDEX];

        if (arguments.getUserRepository().logIn(username, password)) {
            return arguments.getUserSessionManager().addSession(socketChannel, username);
        } else {
            return INCORRECT_PASSWORD;
        }
    }

}
