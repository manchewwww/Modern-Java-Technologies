package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.server.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.DataRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListOfferingsCommandTest {

    private static ListOfferingsCommand command;
    private static CacheData mockCacheData;

    @BeforeAll
    public static void setUp() {
        mockCacheData = mock();
        DataRepository mockDataRepository = mock();
        when(mockDataRepository.getCacheData()).thenReturn(mockCacheData);
        Arguments arguments = Arguments.builder(null, mockDataRepository, null).build();

        command = new ListOfferingsCommand(arguments);
    }

    @Test
    public void testExecuteWithInvalidArgumentsListOfferingsCommand() {
        String[] args = new String[] {"100"};

        assertThrows(InvalidCountOfArgumentsException.class, () -> command.execute(args),
            "When list-offerings command is called with invalid count of arguments InvalidCountOfArgumentsException should be thrown");
    }

    @Test
    public void testExecuteListOfferingsCommand() throws InvalidCountOfArgumentsException {
        String[] args = new String[] {};

        String result = "availableCryptos";

        when(mockCacheData.listOfferings()).thenReturn("availableCryptos");

        assertEquals(result, command.execute(args),
            "When list-offerings command is called returned message is incorrect");
    }

}
