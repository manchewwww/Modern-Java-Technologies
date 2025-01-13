package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

import java.util.Arrays;

public class CommandExecutor {

    private final CommandFactory commandFactory;

    public CommandExecutor(PollRepository pollRepository) {
        this.commandFactory = new CommandFactory(pollRepository);
    }

    public String execute(String line) {
        line = line.replaceAll("\n", "");
        String[] args = line.split(" ");
        if (args.length == 0) {
            return "\"{\"status\":\"ERROR\",\"message\":\"Invalid command\"}\"";
        }
        String command = args[0];
        args = Arrays.stream(args)
            .skip(1)
            .filter(s -> !s.isBlank())
            .toArray(String[]::new);
        try {
            return commandFactory.getCommand(command).execute(args);
        } catch (InvalidCommandException e) {
            return "\"{\"status\":\"ERROR\",\"message\":\"Invalid command\"}\"";
        }
    }

}
