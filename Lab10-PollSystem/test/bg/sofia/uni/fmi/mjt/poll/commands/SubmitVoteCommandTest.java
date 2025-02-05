package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;
import bg.sofia.uni.fmi.mjt.poll.server.repository.InMemoryPollRepository;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubmitVoteCommandTest {

    private static Command command;

    @BeforeAll
    public static void setUpBefore() throws Exception {
        PollRepository poll = new InMemoryPollRepository();
        poll.addPoll(new Poll("Where", new HashMap<>(){
            {
                put("A",0);
                put("B",0);
            }}));
        poll.addPoll(new Poll("What", new HashMap<>(){
            {
                put("C",0);
                put("D",0);
            }}));
        command = new SubmitVoteCommand(poll);
    }

    @Test
    public void testSubmitVoteCommandExecuteWithDiffArguments() {
        String[] args = new String[] {"asd"};
        assertTrue(command.execute(args).contains("\"status\":\"ERROR\""),
            "When ListPollsCommand execute have different arguments from 2 program expect ERROR status");
    }

    @Test
    public void testSubmitVoteCommandExecuteWithValidArgumentsWithInvalidID() {
        String[] args = new String[] {"asd", "A"};
        assertTrue(command.execute(args).contains("\"status\":\"ERROR\""),
            "When ListPollsCommand execute invalid parsing int expect ERROR status");
    }

    @Test
    public void testSubmitVoteCommandExecuteWithValidArgumentsWithImagineID() {
        String[] args = new String[] {"3000", "A"};
        assertTrue(command.execute(args).contains("\"status\":\"ERROR\""),
            "ListPollsCommand execute does not contain ID expect ERROR status");
    }

    @Test
    public void testSubmitVoteCommandExecuteWithValidArgumentsWithInvalidOption() {
        String[] args = new String[] {"1000", "G"};
        assertTrue(command.execute(args).contains("\"status\":\"ERROR\""),
            "ListPollsCommand execute does not contain option expect ERROR status");
    }

    @Test
    public void testSubmitVoteCommandExecuteWithValidArguments() {
        String[] args = new String[] {"1", "B"};
        assertTrue(command.execute(args).contains("\"status\":\"OK\""),
            "ListPollsCommand execute contain id and key program expect OK status");
    }

}
