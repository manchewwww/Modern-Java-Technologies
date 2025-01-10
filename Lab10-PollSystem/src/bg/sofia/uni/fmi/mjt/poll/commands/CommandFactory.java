package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

public class CommandFactory {

    private final PollRepository pollRepository;

    public CommandFactory(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Command getCommand(String command) {
        return switch (command) {
            case "create-poll" -> new CreatePollCommand(pollRepository);
            case "list-polls" -> new ListPollsCommand(pollRepository);
            case "submit-vote" -> new SubmitVoteCommand(pollRepository);
            default -> throw new InvalidCommandException("Unknown command: " + command);
        };
    }

}
