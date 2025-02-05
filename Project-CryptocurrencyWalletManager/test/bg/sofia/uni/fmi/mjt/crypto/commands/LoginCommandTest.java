package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.LoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginCommandTest {

    private static LoginCommand command;
    private static SocketChannel mockSocketChannel;
    private static UserRepository mockUserRepository;
    private static UserSessionManager mockUserSessionManager;

    @BeforeAll
    public static void setUp() {
        mockSocketChannel = mock();
        mockUserRepository = mock();
        mockUserSessionManager = mock();

        command = new LoginCommand(mockUserRepository, mockUserSessionManager, mockSocketChannel);
    }

    @Test
    public void testExecuteWithInvalidArgumentsLoginCommand() {
        String[] args = new String[] {"user", "pass", "pass1"};

        assertThrows(InvalidCountOfArgumentsException.class, () -> command.execute(args),
            "When login command is called with invalid count of arguments InvalidCountOfArgumentsException should be thrown");
    }

    @Test
    public void testExecuteWithIncorrectPasswordLoginCommand()
        throws InvalidCountOfArgumentsException, UserDoesNotExistsException, LoginException {
        String[] args = new String[] {"user", "pass"};

        String result = "Incorrect password";

        when(mockUserRepository.logIn("user", "pass")).thenReturn(false);
        when(mockUserSessionManager.addSession(mockSocketChannel, "user")).thenReturn(result);

        assertEquals(result, command.execute(args),
            "When login command is called and password is incorrect returned message is incorrect");
    }

    @Test
    public void testExecuteLoginCommand()
        throws InvalidCountOfArgumentsException, UserDoesNotExistsException, LoginException {
        String[] args = new String[] {"user", "pass"};

        String result = "You are log in successfully";

        when(mockUserRepository.logIn("user", "pass")).thenReturn(true);
        when(mockUserSessionManager.addSession(mockSocketChannel, "user")).thenReturn(result);

        assertEquals(result, command.execute(args),
            "When login command is called returned message is incorrect");
    }

}
