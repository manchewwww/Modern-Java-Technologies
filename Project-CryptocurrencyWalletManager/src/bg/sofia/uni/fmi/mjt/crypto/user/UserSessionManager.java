package bg.sofia.uni.fmi.mjt.crypto.user;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserSessionManager {

    private static final Map<SocketChannel, String> SOCKET = new ConcurrentHashMap<>();

    public static String addSession(SocketChannel channel, String username) {
        if (SOCKET.containsKey(channel)) {
            throw new IllegalArgumentException("You are logged in profile!");
        }

        SOCKET.put(channel, username);
        return "You are log in successfully";
    }

    public static String removeSession(SocketChannel channel) {
        if (!SOCKET.containsKey(channel)) {
            throw new IllegalArgumentException("You are not logged in profile!");
        }

        SOCKET.remove(channel);
        return "You are logged out successfully";
    }

    public static String getUsername(SocketChannel channel) {
        if (!SOCKET.containsKey(channel)) {
            throw new IllegalArgumentException("You are not logged in profile!");
        }

        return SOCKET.get(channel);
    }

}
