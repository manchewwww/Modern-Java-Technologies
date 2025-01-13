package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;
import bg.sofia.uni.fmi.mjt.poll.server.repository.InMemoryPollRepository;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListPollsCommandTest {

    private static Command cmd;

    @BeforeAll
    public static void setUp() throws Exception {
        PollRepository pollRepository = new InMemoryPollRepository();
        cmd = new ListPollsCommand(pollRepository);
    }

    @Test
    public void testListPollsExecuteWithArguments() {
        String[] args = new String[] {"asd"};
        assertTrue(cmd.execute(args).contains("\"status\":\"ERROR\""),
            "When ListPollsCommand execute have arguments program expect ERROR status");
    }

    @Test
    public void testListPollsExecuteWithoutArgumentsWithNothingToShow() {
        assertTrue(cmd.execute(new String[]{}).contains("\"status\":\"ERROR\""),
            "When ListPollsCommand execute have nothing to show program expect ERROR status");
    }

    @Test
    public void testListPollsExecuteWithoutArgumentsWith() {
        PollRepository pollRepository = new InMemoryPollRepository();
        Command cmd = new ListPollsCommand(pollRepository);
        pollRepository.addPoll(new Poll("Ques", Map.of("asd",0,"asda",2)));
        pollRepository.addPoll(new Poll("Question", Map.of("asd",0,"asda",2)));
        assertTrue(cmd.execute(new String[]{}).contains("\"status\":\"OK\""),
            "When ListPollsCommand execute with polls program expect OK status");
    }

}
