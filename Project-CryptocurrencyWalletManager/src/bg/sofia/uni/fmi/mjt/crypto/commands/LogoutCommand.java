package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;

import java.nio.channels.SocketChannel;

public class LogoutCommand implements Command {

    private static final int ARGS_LENGTH = 0;

    private final SocketChannel socketChannel;
    private final Arguments arguments;

    public LogoutCommand(Arguments arguments, SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.arguments = arguments;
    }

    @Override
    public String execute(String[] args) throws InvalidCountOfArgumentsException, NotLoginException {
        if (args.length != ARGS_LENGTH) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        return arguments.getUserSessionManager().removeSession(socketChannel);
    }

}
