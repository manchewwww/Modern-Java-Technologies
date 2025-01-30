package bg.sofia.uni.fmi.mjt.crypto.server.repository;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserExistsException;
import bg.sofia.uni.fmi.mjt.crypto.user.User;

public interface UserRepository {

    String addUser(String username, String password) throws UserExistsException;

    boolean logIn(String username, String password) throws UserDoesNotExistsException;

    User getUser(String username);

}
