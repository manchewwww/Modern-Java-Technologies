package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HelpCommandTest {

    private static HelpCommand helpCommand;

    @BeforeAll
    public static void setUp() {
        helpCommand = new HelpCommand();
    }

    @Test
    public void testExecuteWithInvalidArgumentsHelpCommand() {
        String[] args = {"me"};

        assertThrows(InvalidCountOfArgumentsException.class, () -> helpCommand.execute(args),
            "When help command is called with invalid count of arguments InvalidCountOfArgumentsException should be thrown");
    }

    @Test
    public void testExecuteHelpCommand()
        throws InvalidCountOfArgumentsException {

        String[] args = new String[]{};

        String result = String.format("Commands: "
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

        assertEquals(result, helpCommand.execute(args),
            "When help command is called invalid text is returned");
    }

}
