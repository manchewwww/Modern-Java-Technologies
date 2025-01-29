package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;

import java.nio.channels.SocketChannel;

public class LogOutCommand implements Command {

    private final SocketChannel socketChannel;

    public LogOutCommand(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public String execute(String[] args) throws InvalidCountOfArgumentsException {
        if (args.length != 0) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        return UserSessionManager.removeSession(socketChannel);
    }

}
