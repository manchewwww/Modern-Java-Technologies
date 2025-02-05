package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.server.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.DataRepository;
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

public class SellCommandTest {

    private static SellCommand command;

    private static UserRepository mockUserRepository;
    private static UserSessionManager mockUserSessionManager;
    private static User mockUser;
    private static CacheData mockCacheData;

    @BeforeAll
    public static void setUp() {
        mockUserRepository = mock();
        SocketChannel mockSocketChannel = mock();
        mockUserSessionManager = mock();
        mockUser = mock();
        mockCacheData = mock();

        command = new SellCommand(mockUserRepository, mockUserSessionManager, mockSocketChannel, mockCacheData);
    }

    @Test
    public void testExecuteWithInvalidArgumentsSell() {
        String[] args = new String[] {"BTC", "BTC", "100"};

        assertThrows(InvalidCountOfArgumentsException.class, () -> command.execute(args),
            "When sell command is called with invalid count of arguments InvalidCountOfArgumentsException should be thrown");
    }

    @Test
    public void testExecuteSellCommand()
        throws NotLoginException, InvalidCountOfArgumentsException, UserDoesNotExistsException, InvalidAmountException,
        CryptoNotFoundException {
        String[] args = new String[] {"BTC"};

        String result = "The sale is successful!";

        when(mockCacheData.getPriceFromAssetId("BTC")).thenReturn(1000.0);
        when(mockUserSessionManager.getUsername(any())).thenReturn("ivan");
        when(mockUserRepository.getUser("ivan")).thenReturn(mockUser);
        when(mockUser.sellCrypto("BTC", 1000.0)).thenReturn(result);

        assertEquals(result, command.execute(args),
            "When sell command is called returned message is incorrect");
    }

}
