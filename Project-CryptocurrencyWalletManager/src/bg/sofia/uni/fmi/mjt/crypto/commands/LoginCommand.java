package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.LoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;

import java.nio.channels.SocketChannel;

public class LoginCommand implements Command {

    private static final int USERNAME_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    private static final String INCORRECT_PASSWORD = "Incorrect password";

    private final UserRepository userRepository;
    private final SocketChannel socketChannel;
    private final UserSessionManager userSessionManager;

    public LoginCommand(UserRepository userRepository, UserSessionManager userSessionManager,
                        SocketChannel socketChannel) {
        this.userRepository = userRepository;
        this.socketChannel = socketChannel;
        this.userSessionManager = userSessionManager;
    }

    @Override
    public String execute(String[] args)
        throws UserDoesNotExistsException, InvalidCountOfArgumentsException, LoginException {
        if (args.length != 2) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        String username = args[USERNAME_INDEX];
        String password = args[PASSWORD_INDEX];

        if (userRepository.logIn(username, password)) {
            return userSessionManager.addSession(socketChannel, username);
        } else {
            return INCORRECT_PASSWORD;
        }
    }

}
