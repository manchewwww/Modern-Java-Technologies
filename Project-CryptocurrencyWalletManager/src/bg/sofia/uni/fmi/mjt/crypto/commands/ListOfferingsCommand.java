package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;

public class ListOfferingsCommand implements Command {

    private static final int ARGS_LENGTH = 0;

    private final Arguments arguments;

    public ListOfferingsCommand(Arguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public String execute(String[] args) throws InvalidCountOfArgumentsException {
        if (args.length != ARGS_LENGTH) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        return arguments.getDataRepository().getCacheData().listOfferings();
    }

}
