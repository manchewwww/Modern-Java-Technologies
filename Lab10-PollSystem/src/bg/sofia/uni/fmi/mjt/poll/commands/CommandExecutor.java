package bg.sofia.uni.fmi.mjt.poll.commands;

import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

import java.util.Arrays;

public class CommandExecutor {

    private final CommandFactory commandFactory;

    public CommandExecutor(PollRepository pollRepository) {
        this.commandFactory = new CommandFactory(pollRepository);
    }

    public String execute(String line) {
        String[] args = line.split(" ");
        String command = args[0];
        args = Arrays.stream(args)
            .skip(1)
            .filter(s -> !s.isEmpty())
            .toArray(String[]::new);

        return commandFactory.getCommand(command).execute(args);
    }

}
