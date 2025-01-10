package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

import java.util.Map;

public class ListPollsCommand implements Command {

    private final PollRepository pollRepository;

    public ListPollsCommand(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 0) {
            return "{\"status\":\"ERROR\",\"message\":\"Usage: list-polls\"}";
        }

        Map<Integer, Poll> polls = pollRepository.getAllPolls();
        StringBuilder sb = new StringBuilder();

        if (polls.isEmpty()) {
            sb.append("{\"status\":\"ERROR\",\"message\":\"No active polls available.\"}");
        } else {
            sb.append("{\"status\":\"OK\",\"polls\":{");

            int sizeOfPolls = polls.size();
            int currentIndex = 0;

            for (Map.Entry<Integer, Poll> poll : polls.entrySet()) {
                sb.append("\"").append(poll.getKey()).append("\":{").append(toJSON(poll.getValue())).append("}");
                if ((++currentIndex) < sizeOfPolls) {
                    sb.append(",");
                }
            }
            sb.append("}}");
        }
        return sb.toString();
    }

    private String toJSON(Poll poll) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"question\":\"").append(poll.question()).append("\",\"options\":{");

        int sizeOfOptions = poll.options().size();
        int currentIndex = 0;

        for (Map.Entry<String, Integer> option : poll.options().entrySet()) {
            sb.append("\"").append(option.getKey()).append("\":").append(option.getValue());
            if ((++currentIndex) < sizeOfOptions) {
                sb.append(",");
            }
        }

        sb.append("}");

        return sb.toString();
    }

}
