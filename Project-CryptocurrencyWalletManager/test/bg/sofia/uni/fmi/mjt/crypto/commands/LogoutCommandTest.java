package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogoutCommandTest {

    private static LogoutCommand command;
    private static UserSessionManager mockUserSessionManager;
    private static SocketChannel mockSocketChannel;

    @BeforeAll
    public static void setUp() {
        mockUserSessionManager = mock();
        mockSocketChannel = mock();
        Arguments arguments = Arguments.builder(null, null, mockUserSessionManager).build();


        command = new LogoutCommand(arguments, mockSocketChannel);
    }

    @Test
    public void testExecuteWithInvalidArgumentsLogoutCommand() {
        String[] args = new String[] {"invalid"};

        assertThrows(InvalidCountOfArgumentsException.class, () -> command.execute(args),
            "When logout command is called with invalid count of arguments InvalidCountOfArgumentsException should be thrown");
    }

    @Test
    public void testExecuteLogoutCommand()
        throws InvalidCountOfArgumentsException, NotLoginException {
        String[] args = new String[] {};

        String result = "You are logged out successfully";

        when(mockUserSessionManager.removeSession(mockSocketChannel)).thenReturn(result);

        assertEquals(result, command.execute(args),
            "When logout command is called returned message is incorrect");
    }

}
