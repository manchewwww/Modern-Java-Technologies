package bg.sofia.uni.fmi.mjt.poll.server.repository;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;

import java.util.Map;

public class InMemoryPollRepository implements PollRepository {

    @Override
    public int addPoll(Poll poll) {
        return 0;
    }

    @Override
    public Poll getPoll(int pollId) {
        return null;
    }

    @Override
    public Map<Integer, Poll> getAllPolls() {
        return Map.of();
    }

    @Override
    public void clearAllPolls() {

    }

}
