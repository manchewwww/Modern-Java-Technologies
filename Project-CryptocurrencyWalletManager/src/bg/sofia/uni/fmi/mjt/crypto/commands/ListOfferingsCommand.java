package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.api.data.CacheData;

public class ListOfferingsCommand implements Command {

    private final CacheData cacheData;

    public ListOfferingsCommand(CacheData cacheData) {
        this.cacheData = cacheData;
    }

    @Override
    public String execute(String[] args) throws InvalidCountOfArgumentsException {
        if (args.length != 0) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        return cacheData.listOfferings();
    }

}
