package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.server.repository.InMemoryPollRepository;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandExecutorTest {

    static CommandExecutor commandExecutor;

    @BeforeAll
    public static void setUp() throws Exception {
        PollRepository pollRepository = new InMemoryPollRepository();
        commandExecutor = new CommandExecutor(pollRepository);
    }

    @Test
    public void testExecuteWithInvalidCommand() {
        assertTrue(commandExecutor.execute("InvalidCommand question answer1 answer2").contains("\"status\":\"ERROR\""),
            "When command is invalid program expect ERROR status");
    }

    @Test
    public void testExecuteWithValidCommand() {
        assertTrue(commandExecutor.execute("create-poll question answer1 answer2").contains("\"status\":\"OK\""),
            "When command is valid program expect OK status");
    }

    @Test
    public void testExecuteWithNewLine() {
        assertTrue(commandExecutor.execute("\n").contains("\"status\":\"ERROR\""),
            "When command is invalid program expect ERROR status");
    }

}
