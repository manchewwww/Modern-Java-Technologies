package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

public class SubmitVoteCommand implements Command {

    private final PollRepository pollRepository;

    public SubmitVoteCommand(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 2) {
            return "{\"status\":\"ERROR\",\"message\":\"Usage: submit-vote <id> <option>\"}";
        }

        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return "\"{\"status\":\"ERROR\",\"message\":\"Invalid command\"}\"";
        }

        Poll poll = pollRepository.getPoll(id);
        if (poll == null) {
            return "{\"status\":\"ERROR\",\"message\":\"Poll with ID " + id + " does not exist.\"}";
        }

        String option = args[1];
        if (!poll.options().containsKey(option)) {
            return "{\"status\":\"ERROR\",\"message\":\"Invalid option. Option " + option + " does not exist.\"}";
        }

        synchronized (poll) {
            poll.options().put(option, poll.options().get(option) + 1);
        }

        return "{\"status\":\"OK\",\"message\":\"Vote submitted successfully for option: " + option + "\"}";
    }

}
