package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CreatePollCommand implements Command {

    private static final int MIN_NUMBER_OF_ARGUMENTS = 3;
    private final PollRepository pollRepository;

    public CreatePollCommand(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    public String execute(String[] args) {
        if (args.length < MIN_NUMBER_OF_ARGUMENTS) {
            return "{\"status\":\"ERROR\",\"message\":\"Usage: create-poll <question> <option-1> <option-2> [... <option-N>]\"}";
        }

        String question = args[0];
        Map<String, Integer> options =
            Arrays.stream(args)
            .skip(1)
            .collect(Collectors.toMap(option -> option, option -> 0));

        int id = pollRepository.addPoll(new Poll(question, options));

        return "{\"status\":\"OK\",\"message\":\"Poll " + id + " created successfully.\"}";
    }

}
