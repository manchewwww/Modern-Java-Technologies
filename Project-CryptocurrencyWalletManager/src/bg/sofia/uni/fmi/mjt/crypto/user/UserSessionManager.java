package bg.sofia.uni.fmi.mjt.crypto.user;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.LoginException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserSessionManager {

    private static final String LOGIN_SUCCESSFUL = "You are log in successfully";
    private static final String LOGOUT_SUCCESSFUL = "You are logged out successfully";

    private final Map<SocketChannel, String> loginSocketChannels;

    public UserSessionManager() {
        loginSocketChannels = new ConcurrentHashMap<>();
    }

    public String addSession(SocketChannel channel, String username) throws LoginException {
        if (loginSocketChannels.containsKey(channel)) {
            throw new LoginException(ErrorMessages.YOU_ARE_LOGIN_MESSAGE);
        }

        loginSocketChannels.put(channel, username);
        return LOGIN_SUCCESSFUL;
    }

    public String removeSession(SocketChannel channel) throws NotLoginException {
        if (!loginSocketChannels.containsKey(channel)) {
            throw new NotLoginException(ErrorMessages.YOU_ARE_NOT_LOGIN_MESSAGE);
        }

        loginSocketChannels.remove(channel);
        return LOGOUT_SUCCESSFUL;
    }

    public String getUsername(SocketChannel channel) throws NotLoginException {
        if (!loginSocketChannels.containsKey(channel)) {
            throw new NotLoginException(ErrorMessages.YOU_ARE_NOT_LOGIN_MESSAGE);
        }

        return loginSocketChannels.get(channel);
    }

}
