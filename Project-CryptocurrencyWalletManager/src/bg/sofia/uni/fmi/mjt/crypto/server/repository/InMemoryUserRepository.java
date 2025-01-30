package bg.sofia.uni.fmi.mjt.crypto.server.repository;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.hasher.PasswordHasher;
import bg.sofia.uni.fmi.mjt.crypto.user.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

    private static final String SUCCESSFUL_REGISTER = "Register successful";

    private final Map<String, User> users;

    public InMemoryUserRepository() {
        users = new ConcurrentHashMap<String, User>();
    }

    @Override
    public String addUser(String username, String password) throws UserExistsException {
        if (userExists(username)) {
            throw new UserExistsException(String.format(ErrorMessages.USER_EXISTS_MESSAGE, username));
        }

        users.putIfAbsent(username, new User(username, PasswordHasher.hashPassword(password)));
        return SUCCESSFUL_REGISTER;
    }

    @Override
    public boolean logIn(String username, String password) throws UserDoesNotExistsException {
        if (!userExists(username)) {
            throw new UserDoesNotExistsException(String.format(ErrorMessages.USER_DOES_NOT_EXISTS_MESSAGE, username));
        }

        return PasswordHasher.verifyPassword(password, users.get(username).getHashedPassword());
    }

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    private boolean userExists(String username) {
        return users.containsKey(username);
    }

}
