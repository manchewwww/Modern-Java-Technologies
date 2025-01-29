package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;

public class HelpCommand implements Command {

    @Override
    public String execute(String[] args) throws InvalidCountOfArgumentsException {
        if (args.length != 0) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        return String.format("Commands: "
                + System.lineSeparator() + "1. %s"
                + System.lineSeparator() + "2. %s"
                + System.lineSeparator() + "3. %s"
                + System.lineSeparator() + "4. %s"
                + System.lineSeparator() + "5. %s"
                + System.lineSeparator() + "6. %s"
                + System.lineSeparator() + "7. %s"
                + System.lineSeparator() + "8. %s"
                + System.lineSeparator() + "9. %s"
            , "register username password", "login username password",
            "logout", "deposit-money amount", "buy offering-code amount",
            "sell offering-code", "list-offerings", "get-wallet-summary",
            "get-wallet-overall-summary");
    }

}