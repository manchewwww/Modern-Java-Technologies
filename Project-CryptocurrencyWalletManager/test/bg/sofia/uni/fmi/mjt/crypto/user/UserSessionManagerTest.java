package bg.sofia.uni.fmi.mjt.crypto.user;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.LoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserSessionManagerTest {

    private UserSessionManager userSessionManager;
    private SocketChannel socketChannel;
    private static final String USERNAME = "Georgi";

    @BeforeEach
    public void setUp() throws IOException {
        socketChannel = SocketChannel.open();
        userSessionManager = new UserSessionManager();
    }

    @Test
    public void testAddSessionWhenSocketIsLogin() throws LoginException {
        userSessionManager.addSession(socketChannel, USERNAME);

        assertThrows(LoginException.class, () -> userSessionManager.addSession(socketChannel, USERNAME),
            "When socket exist in session LoginException should be thrown");
    }

    @Test
    public void testAddSession() throws LoginException {
        String result = "You are log in successfully";

        assertEquals(result, userSessionManager.addSession(socketChannel, USERNAME),
            "When session is added successfully returned message is incorrect");
    }

    @Test
    public void testRemoveSessionWhenSocketIsNotLogin() {
        assertThrows(NotLoginException.class, () -> userSessionManager.removeSession(socketChannel),
            "When socket is does not exist in session NotLoginException should be thrown");
    }

    @Test
    public void testRemoveSession() throws NotLoginException, LoginException {
        String result = "You are logged out successfully";

        userSessionManager.addSession(socketChannel, USERNAME);

        assertEquals(result, userSessionManager.removeSession(socketChannel),
            "When session is removed successfully returned message is incorrect");
    }

    @Test
    public void testGetUsernameWhenSocketIsNotLogin() {
        assertThrows(NotLoginException.class, () -> userSessionManager.getUsername(socketChannel),
            "When socket is does not exist in session NotLoginException should be thrown");
    }

    @Test
    public void testGetUsername() throws NotLoginException, LoginException {
        userSessionManager.addSession(socketChannel, USERNAME);

        assertEquals(USERNAME, userSessionManager.getUsername(socketChannel),
            "Failed to retrieve the correct username from the socket channel.");
    }

}
