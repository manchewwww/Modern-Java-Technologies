package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.DataRepository;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class CommandFactoryTest {

    private static CommandFactory commandFactory;
    private static SocketChannel socketChannel;

    @BeforeAll
    public static void setUp() {
        UserRepository userRepository = mock();
        DataRepository dataRepository = mock();
        UserSessionManager userSessionManager = mock();
        socketChannel = mock();
        Arguments arguments = Arguments.builder(userRepository, dataRepository, userSessionManager).build();

        commandFactory = new CommandFactory(arguments);
    }

    @Test
    public void testCommandFactoryRegisterCommand() throws InvalidCommandException {
        assertEquals(commandFactory.getCommand("register", socketChannel).getClass(), RegisterCommand.class,
            "Invalid return of command factory. Program expect RegisterCommand");
    }

    @Test
    public void testCommandFactoryLoginCommand() throws InvalidCommandException {
        assertEquals(commandFactory.getCommand("login", socketChannel).getClass(), LoginCommand.class,
            "Invalid return of command factory. Program expect LoginCommand");
    }

    @Test
    public void testCommandFactoryLogoutCommand() throws InvalidCommandException {
        assertEquals(commandFactory.getCommand("logout", socketChannel).getClass(), LogoutCommand.class,
            "Invalid return of command factory. Program expect LogoutCommand");
    }

    @Test
    public void testCommandFactoryDepositMoneyCommand() throws InvalidCommandException {
        assertEquals(commandFactory.getCommand("deposit-money", socketChannel).getClass(), DepositMoneyCommand.class,
            "Invalid return of command factory. Program expect DepositMoneyCommand");
    }

    @Test
    public void testCommandFactoryBuyCommand() throws InvalidCommandException {
        assertEquals(commandFactory.getCommand("buy", socketChannel).getClass(), BuyCommand.class,
            "Invalid return of command factory. Program expect BuyCommand");
    }

    @Test
    public void testCommandFactorySellCommand() throws InvalidCommandException {
        assertEquals(commandFactory.getCommand("sell", socketChannel).getClass(), SellCommand.class,
            "Invalid return of command factory. Program expect SellCommand");
    }

    @Test
    public void testCommandFactoryHelpCommand() throws InvalidCommandException {
        assertEquals(commandFactory.getCommand("help", socketChannel).getClass(), HelpCommand.class,
            "Invalid return of command factory. Program expect HelpCommand");
    }

    @Test
    public void testCommandFactoryListOfferingsCommand() throws InvalidCommandException {
        assertEquals(commandFactory.getCommand("list-offerings", socketChannel).getClass(), ListOfferingsCommand.class,
            "Invalid return of command factory. Program expect ListOfferingsCommand");
    }

    @Test
    public void testCommandFactoryGetWalletSummaryCommand() throws InvalidCommandException {
        assertEquals(commandFactory.getCommand("get-wallet-summary", socketChannel).getClass(),
            GetWalletSummaryCommand.class,
            "Invalid return of command factory. Program expect GetWalletSummaryCommand");
    }

    @Test
    public void testCommandFactoryGetWalletOverallSummaryCommand() throws InvalidCommandException {
        assertEquals(commandFactory.getCommand("get-wallet-overall-summary", socketChannel).getClass(),
            GetWalletOverallSummaryCommand.class,
            "Invalid return of command factory. Program expect GetWalletOverallSummaryCommand");
    }

    @Test
    public void testCommandFactoryInvalidCommand() {
        assertThrows(InvalidCommandException.class,
            () -> commandFactory.getCommand("invalid", socketChannel).getClass(),
            "Invalid return of command factory. Program expect InvalidCommandException");
    }

}
