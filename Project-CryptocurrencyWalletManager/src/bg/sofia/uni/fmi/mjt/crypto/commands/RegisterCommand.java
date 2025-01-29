package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;

public class RegisterCommand implements Command {

    private static final int USERNAME_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    private final UserRepository userRepository;

    public RegisterCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String execute(String[] args) throws UserExistsException, InvalidCountOfArgumentsException {
        if (args.length != 2) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        String username = args[USERNAME_INDEX];
        String password = args[PASSWORD_INDEX];

        return userRepository.addUser(username, password);
    }

}
