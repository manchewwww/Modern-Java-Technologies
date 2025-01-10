package bg.sofia.uni.fmi.mjt.poll.server.repository;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryPollRepository implements PollRepository {

    private static final AtomicInteger ID = new AtomicInteger(1);
    private final Map<Integer, Poll> polls = new ConcurrentHashMap<>();

    @Override
    public int addPoll(Poll poll) {
        if  (poll == null) {
            throw new IllegalArgumentException("Poll is null");
        }

        int id = ID.getAndIncrement();
        polls.put(id, poll);
        return id;
    }

    @Override
    public Poll getPoll(int pollId) {
        return polls.get(pollId);
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
