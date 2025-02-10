package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserExistsException;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterCommandTest {

    private static RegisterCommand command;
    private static UserRepository mockUserRepository;

    @BeforeAll
    public static void setUp() {
        mockUserRepository = mock();
        Arguments arguments = Arguments.builder(mockUserRepository, null, null).build();
        command = new RegisterCommand(arguments);
    }

    @Test
    public void testExecuteWithInvalidArgumentsRegisterCommand() {
        String[] args = new String[] {"user", "pass", "pass1"};

        assertThrows(InvalidCountOfArgumentsException.class, () -> command.execute(args),
            "When register command is called with invalid count of arguments InvalidCountOfArgumentsException should be thrown");
    }

    @Test
    public void testExecuteRegisterCommand()
        throws UserExistsException, InvalidCountOfArgumentsException {
        String[] args = new String[] {"user", "pass"};

        String result = "Register successful";

        when(mockUserRepository.addUser("user", "pass")).thenReturn(result);

        assertEquals(result, command.execute(args),
            "When register command is called returned message is incorrect");
    }

}
