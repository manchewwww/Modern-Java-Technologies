package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;

import java.nio.channels.SocketChannel;

public class LogoutCommand implements Command {

    private final SocketChannel socketChannel;
    private final UserSessionManager userSessionManager;

    public LogoutCommand(UserSessionManager userSessionManager, SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.userSessionManager = userSessionManager;
    }

    @Override
    public String execute(String[] args) throws InvalidCountOfArgumentsException, NotLoginException {
        if (args.length != 0) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        return userSessionManager.removeSession(socketChannel);
    }

}
