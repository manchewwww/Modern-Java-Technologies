package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
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

public class BuyCommandTest {

    private static BuyCommand command;

    private static UserRepository mockUserRepository;
    private static DataRepository mockDataRepository;
    private static UserSessionManager mockUserSessionManager;
    private static User mockUser;
    private static CacheData mockCacheData;

    @BeforeAll
    public static void setUp() {
        mockDataRepository = mock();
        mockUserRepository = mock();
        SocketChannel mockSocketChannel = mock();
        mockUserSessionManager = mock();
        mockUser = mock();
        mockCacheData = mock();

        command = new BuyCommand(mockUserRepository, mockUserSessionManager, mockSocketChannel, mockCacheData);
    }

    @Test
    public void testExecuteWithInvalidArgumentsBuyCommand() {
        String[] args = new String[] {"BTC", "BTC", "100"};

        assertThrows(InvalidCountOfArgumentsException.class, () -> command.execute(args),
            "When buy command is called with invalid count of arguments InvalidCountOfArgumentsException should be thrown");
    }

    @Test
    public void testExecuteBuyCommand()
        throws NotLoginException, InvalidCountOfArgumentsException, UserDoesNotExistsException, InvalidAmountException,
        InsufficientFundsException, CryptoNotFoundException {
        String[] args = new String[] {"BTC", "100"};

        String result = "The purchase is successful!";

        when(mockDataRepository.getCacheData()).thenReturn(mockCacheData);
        when(mockCacheData.getPriceFromAssetId("BTC")).thenReturn(1000.0);
        when(mockUserSessionManager.getUsername(any())).thenReturn("ivan");
        when(mockUserRepository.getUser("ivan")).thenReturn(mockUser);
        when(mockUser.buyCrypto("BTC", 100, 1000.0)).thenReturn(result);

        assertEquals(result, command.execute(args),
            "When buy command is called returned message is incorrect");
    }

}
