package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;

public class RegisterCommand implements Command {

    private static final int ARGS_LENGTH = 2;

    private static final int USERNAME_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    private final Arguments arguments;

    public RegisterCommand(Arguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public String execute(String[] args) throws UserExistsException, InvalidCountOfArgumentsException {
        if (args.length != ARGS_LENGTH) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        String username = args[USERNAME_INDEX];
        String password = args[PASSWORD_INDEX];

        return arguments.getUserRepository().addUser(username, password);
    }

}
