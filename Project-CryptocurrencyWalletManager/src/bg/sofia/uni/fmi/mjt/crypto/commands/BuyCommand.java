package bg.sofia.uni.fmi.mjt.crypto.commands;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;

import java.nio.channels.SocketChannel;

public class BuyCommand implements Command {

    private static final int ARGS_LENGTH = 2;
    private static final int ASSET_ID_INDEX = 0;
    private static final int AMOUNT_INDEX = 1;

    private final Arguments arguments;
    private final SocketChannel socketChannel;

    public BuyCommand(Arguments arguments, SocketChannel socketChannel) {
        this.arguments = arguments;
        this.socketChannel = socketChannel;
    }

    @Override
    public String execute(String[] args)
        throws InvalidCountOfArgumentsException, InvalidAmountException,
        InsufficientFundsException, CryptoNotFoundException, NotLoginException, UserDoesNotExistsException {
        if (args.length != ARGS_LENGTH) {
            throw new InvalidCountOfArgumentsException(ErrorMessages.INVALID_NUMBER_OF_ARGUMENTS);
        }

        String assetId = args[ASSET_ID_INDEX];
        double amount = Double.parseDouble(args[AMOUNT_INDEX]);

        String username = arguments.getUserSessionManager().getUsername(socketChannel);

        return arguments.getUserRepository().getUser(username)
            .buyCrypto(assetId, amount, arguments.getDataRepository().getCacheData().getPriceFromAssetId(assetId));
    }

}
