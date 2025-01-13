package bg.sofia.uni.fmi.mjt.poll.server.repository;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryPollRepository implements PollRepository {

    private final AtomicInteger iD = new AtomicInteger(1);
    private final Map<Integer, Poll> polls = new ConcurrentHashMap<>();

    @Override
    public int addPoll(Poll poll) {
        int id = iD.getAndIncrement();
        polls.put(id, poll);
        return id;
    }

    @Override
    public Poll getPoll(int pollId) {
        return polls.getOrDefault(pollId, null);
    }

    @Override
    public Map<Integer, Poll> getAllPolls() {
        return Collections.unmodifiableMap(polls);
    }

    @Override
    public void clearAllPolls() {
        polls.clear();
    }

}
