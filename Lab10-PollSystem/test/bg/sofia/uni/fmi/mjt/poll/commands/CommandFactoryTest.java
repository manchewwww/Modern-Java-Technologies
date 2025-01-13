package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.poll.server.repository.InMemoryPollRepository;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandFactoryTest {

    private static CommandFactory commandFactory;

    @BeforeAll
    public static void setUp() throws Exception {
        PollRepository pollRepository = new InMemoryPollRepository();
        commandFactory = new CommandFactory(pollRepository);
    }

    @Test
    public void testCommandFactoryCreatePollCommand() {
        assertEquals(commandFactory.getCommand("create-poll").getClass(), CreatePollCommand.class,
            "Invalid return of command factory. Program expect CreatePollCommand");
    }

    @Test
    public void testCommandFactoryListPollCommand() {
        assertEquals(commandFactory.getCommand("list-polls").getClass(), ListPollsCommand.class,
            "Invalid return of command factory. Program expect ListPollsCommand");
    }

    @Test
    public void testCommandFactorySubmitVoteCommand() {
        assertEquals(commandFactory.getCommand("submit-vote").getClass(), SubmitVoteCommand.class,
            "Invalid return of command factory. Program expect SubmitVoteCommand");
    }

    @Test
    public void testCommandFactoryInvalidCommand() {
        assertThrows(InvalidCommandException.class, () -> commandFactory.getCommand("yes"),
            "InvalidCommandException should be thrown when command is invalid");
    }

}
