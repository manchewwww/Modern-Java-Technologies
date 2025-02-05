package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.User;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetWalletSummaryCommandTest {

    private static GetWalletSummaryCommand command;

    private static UserRepository mockUserRepository;
    private static UserSessionManager mockUserSessionManager;
    private static User mockUser;

    @BeforeAll
    public static void setUp() {
        mockUserRepository = mock();
        mockUserSessionManager = mock();
        mockUser = mock();
        SocketChannel mockSocketChanel = mock();
        command = new GetWalletSummaryCommand(mockUserRepository, mockUserSessionManager, mockSocketChanel);
    }

    @Test
    public void testExecuteWithInvalidArgumentsGetWalletSummaryCommand() {
        String[] args = new String[] {"100"};

        assertThrows(InvalidCountOfArgumentsException.class, () -> command.execute(args),
            "When get-wallet-summary command is called with invalid count of arguments InvalidCountOfArgumentsException should be thrown");
    }

    @Test
    public void testExecuteGetWalletSummaryCommand()
        throws NotLoginException, InvalidCountOfArgumentsException, UserDoesNotExistsException {
        String[] args = new String[] {};

        String result = "get-wallet-summary";

        when(mockUserSessionManager.getUsername(any())).thenReturn("ivan");
        when(mockUserRepository.getUser("ivan")).thenReturn(mockUser);
        when(mockUser.getWalletSummary()).thenReturn(result);

        assertEquals(result, command.execute(args),
            "When get wallet summary command is called returned message is incorrect");
    }

}
