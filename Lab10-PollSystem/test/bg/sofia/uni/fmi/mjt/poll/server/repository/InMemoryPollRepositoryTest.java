package bg.sofia.uni.fmi.mjt.poll.server.repository;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryPollRepositoryTest {

    @Test
    public void testAddPoll() {
        PollRepository repository = new InMemoryPollRepository();
        repository.addPoll(new Poll("Quest", Map.of("asd", 0, "qwe", 0)));
        assertEquals(repository.getAllPolls().size(), 1,
            "After one add program expect size of polls to be 1");
    }

    @Test
    public void testClearPoll() {
        PollRepository repository = new InMemoryPollRepository();
        repository.addPoll(new Poll("Quest", Map.of("asd", 0, "qwe", 0)));
        repository.addPoll(new Poll("Quest", Map.of("asd", 0, "qwe", 0)));
        repository.clearAllPolls();
        assertEquals(repository.getAllPolls().size(), 0,
            "After clear poll program expect size of polls to be 0");
    }

}
