package bg.sofia.uni.fmi.mjt.crypto.commands;

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

    private UserRepository mockUserRepository;
    private DataRepository mockDataRepository;
    private SocketChannel mockSocketChannel;
    private UserSessionManager mockUserSessionManager;

    @BeforeEach
    public void setUp() {
        mockDataRepository = mock();
        mockUserRepository = mock();
        mockSocketChannel = mock();
        mockUserSessionManager = mock();

        commandExecutor = new CommandExecutor(mockUserRepository, mockDataRepository, mockUserSessionManager);
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
