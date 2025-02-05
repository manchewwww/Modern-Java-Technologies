package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.server.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.User;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.channels.SocketChannel;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetWalletOverallSummaryCommandTest {

    private static GetWalletOverallSummaryCommand command;

    private static UserRepository mockUserRepository;
    private static UserSessionManager mockUserSessionManager;
    private static User mockUser;
    private static CacheData mockCacheData;

    @BeforeAll
    public static void setUp() {
        mockUserRepository = mock();
        mockUserSessionManager = mock();
        mockUser = mock();
        mockCacheData = mock();
        SocketChannel mockSocketChanel = mock();

        command = new GetWalletOverallSummaryCommand(mockUserRepository, mockUserSessionManager, mockSocketChanel,
            mockCacheData);
    }

    @Test
    public void testExecuteWithInvalidArgumentsGetWalletOverallSummaryCommand() {
        String[] args = new String[] {"Invalid"};

        assertThrows(InvalidCountOfArgumentsException.class, () -> command.execute(args),
            "When get-wallet-overall-summary command is called with invalid count of arguments InvalidCountOfArgumentsException should be thrown");
    }

    @Test
    public void testExecuteGetWalletOverallSummaryCommand()
        throws NotLoginException, InvalidCountOfArgumentsException, UserDoesNotExistsException,
        CryptoNotFoundException {
        String[] args = new String[] {};

        String result = "get-wallet-overall-summary";

        when(mockUserSessionManager.getUsername(any())).thenReturn("ivan");
        when(mockUserRepository.getUser("ivan")).thenReturn(mockUser);
        when(mockCacheData.getPrices()).thenReturn(Map.of("BTC", 100.0));
        when(mockUser.getWalletOverallSummary(Map.of("BTC", 100.0))).thenReturn(result);

        assertEquals(result, command.execute(args),
            "When get wallet overall summary command is called returned message is incorrect");
    }

}
