package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.DataRepository;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class CommandExecutorTest {

    private CommandExecutor commandExecutor;

    private SocketChannel mockSocketChannel;

    @BeforeEach
    public void setUp() {
        DataRepository mockDataRepository = mock();
        UserRepository mockUserRepository = mock();
        mockSocketChannel = mock();
        UserSessionManager mockUserSessionManager = mock();
        Arguments arguments = Arguments.builder(mockUserRepository, mockDataRepository, mockUserSessionManager).build();

        commandExecutor = new CommandExecutor(arguments);
    }

    @Test
    public void testExecuteWithNullLine() {
        assertThrows(IllegalArgumentException.class, () -> commandExecutor.execute(null, mockSocketChannel),
            "When line is null IllegalArgumentException should be thrown");
    }

    @Test
    public void testExecuteWithEmptyCommand() {
        String line = "";
        assertThrows(InvalidCommandException.class, () -> commandExecutor.execute(line, mockSocketChannel),
            "When command is invalid InvalidCommandException should be thrown");
    }

}
