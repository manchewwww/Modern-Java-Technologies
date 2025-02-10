package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountException;
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

public class DepositMoneyCommandTest {

    private static DepositMoneyCommand command;

    private static UserRepository mockUserRepository;
    private static UserSessionManager mockUserSessionManager;
    private static User mockUser;

    @BeforeAll
    public static void setUp() {
        mockUserRepository = mock();
        SocketChannel mockSocketChannel = mock();
        mockUserSessionManager = mock();
        mockUser = mock();

        Arguments arguments = Arguments.builder(mockUserRepository, null, mockUserSessionManager).build();

        command = new DepositMoneyCommand(arguments, mockSocketChannel);
    }

    @Test
    public void testExecuteWithInvalidArgumentsDepositMoneyCommand() {
        String[] args = new String[] {"100", "100"};

        assertThrows(InvalidCountOfArgumentsException.class, () -> command.execute(args),
            "When deposit-money command is called with invalid count of arguments InvalidCountOfArgumentsException should be thrown");
    }

    @Test
    public void testExecuteDepositMoneyCommand()
        throws NotLoginException, InvalidCountOfArgumentsException, UserDoesNotExistsException, InvalidAmountException {
        String[] args = new String[] {"100"};

        String result = "The deposit is successful!";

        when(mockUserSessionManager.getUsername(any())).thenReturn("ivan");
        when(mockUserRepository.getUser("ivan")).thenReturn(mockUser);
        when(mockUser.depositMoney(100)).thenReturn(result);

        assertEquals(result, command.execute(args),
            "When deposit-money command is called returned message is incorrect");
    }

}
