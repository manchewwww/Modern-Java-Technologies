package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.server.repository.InMemoryPollRepository;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreatePollCommandTest {

    private static Command cmd;

    @BeforeAll
    public static void setUp() throws Exception {
        PollRepository pollRepository = new InMemoryPollRepository();
        cmd = new CreatePollCommand(pollRepository);
    }

    @Test
    public void testCreatePollExecuteWithInvalidArgumentCount() {
        String[] args = new String[2];
        args[0] = "Valid";
        args[1] = "Invalid";
        assertTrue(cmd.execute(args).contains("\"status\":\"ERROR\""),
            "Create poll with small than three arguments should return ERROR status");
    }

    @Test
    public void testCreatePollExecuteWithValidArgumentCount() {
        String[] args = new String[3];
        args[0] = "Valid";
        args[1] = "Invalid";
        args[2] = "Valid";
        assertTrue(cmd.execute(args).contains("\"status\":\"OK\""),
            "Create poll with three arguments should return OK status");
    }

}
