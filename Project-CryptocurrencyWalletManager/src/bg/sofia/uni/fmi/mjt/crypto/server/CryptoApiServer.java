package bg.sofia.uni.fmi.mjt.crypto.server;

import bg.sofia.uni.fmi.mjt.crypto.builder.Arguments;
import bg.sofia.uni.fmi.mjt.crypto.commands.CommandExecutor;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InsufficientFundsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidAmountException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.InvalidCountOfArgumentsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.LoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.NotLoginException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserExistsException;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.ApiException;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.DataRepository;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.InMemoryDataRepository;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.server.reader.LoadUserRepositoryFromFile;
import bg.sofia.uni.fmi.mjt.crypto.server.writer.WriteUserRepositoryToFile;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

public class CryptoApiServer {

    private static final String TO_EXIT_MESSAGE = "Type 'exit' to stop the server.";
    private static final String COMMAND_FOR_STOP = "exit";
    private static final String USERS_NOT_SAVE_IN_FILE = "Users are not saved in file. Try again.";
    private static final String FAILED_TO_START = "Failed to start server";
    private static final String SERVER_STOP_MESSAGE = "Server has stopped.";
    private static final String ERROR_IN_CLIENT_REQUEST_MESSAGE = "Error occurred while processing client request: %s";
    private static final String INVALID_COMMAND_MESSAGE = "Invalid command";
    private static final String INVALID_AMOUNT_MESSAGE = "Amount is not a number!";
    private static final int MIN_AVAILABLE_CHANNELS = 0;
    private static final int MIN_BYTES_FOR_READS = 0;

    private static final int SERVER_PORT = 8888;
    private static final int BUFFER_SIZE = 10240;
    private static final String HOST = "localhost";

    private final CommandExecutor commandExecutor;
    private final UserRepository userRepository;
    private final DataRepository dataRepository;

    private final int port;
    private boolean isServerWorking;

    private ByteBuffer buffer;
    private Selector selector;

    public CryptoApiServer(int port) throws ApiException {
        this.port = port;
        userRepository = LoadUserRepositoryFromFile.loadData();
        UserSessionManager userSessionManager = new UserSessionManager();
        dataRepository = new InMemoryDataRepository(this);
        Arguments arguments = Arguments.builder(userRepository, dataRepository, userSessionManager).build();
        this.commandExecutor = new CommandExecutor(arguments);
    }

    public static void main(String[] args) throws ApiException {
        CryptoApiServer server = new CryptoApiServer(SERVER_PORT);
        Thread serverThread = new Thread(server::start);
        serverThread.start();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(TO_EXIT_MESSAGE);
            while (true) {
                String input = scanner.nextLine();

                if (COMMAND_FOR_STOP.equalsIgnoreCase(input)) {
                    try {
                        server.stop();
                    } catch (IOException e) {
                        System.out.println(USERS_NOT_SAVE_IN_FILE);
                        continue;
                    }
                    serverThread.join();
                    break;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(SERVER_STOP_MESSAGE);
    }

    public void start() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            selector = Selector.open();
            configureServerSocketChannel(serverSocketChannel, selector);
            this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
            isServerWorking = true;
            dataRepository.startScheduleAtFixedRate();
            
            while (isServerWorking) {
                try {
                    int readyChannels = selector.select();
                    if (readyChannels == MIN_AVAILABLE_CHANNELS) {
                        continue;
                    }

                    processSelectedKeys();
                } catch (IOException e) {
                    System.out.println(String.format(ERROR_IN_CLIENT_REQUEST_MESSAGE, e.getMessage()));
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(FAILED_TO_START, e);
        }
    }

    public void stop() throws IOException {
        WriteUserRepositoryToFile.writeData(userRepository);
        this.isServerWorking = false;
        dataRepository.shutdown();
        if (selector.isOpen()) {
            selector.wakeup();
        }
    }

    private void configureServerSocketChannel(ServerSocketChannel channel, Selector selector) throws IOException {
        channel.bind(new InetSocketAddress(HOST, this.port));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    private void processSelectedKeys() throws IOException {
        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            if (key.isReadable()) {
                SocketChannel clientChannel = (SocketChannel) key.channel();
                String clientInput = getClientInput(clientChannel);
                if (clientInput == null) {
                    writeClientOutput(clientChannel, INVALID_COMMAND_MESSAGE);
                    continue;
                }

                try {
                    String output = commandExecutor.execute(clientInput, clientChannel);
                    writeClientOutput(clientChannel, output);
                } catch (UserExistsException | InvalidAmountException | UserDoesNotExistsException |
                         InvalidCommandException | InvalidCountOfArgumentsException | InsufficientFundsException |
                         CryptoNotFoundException | NotLoginException | LoginException e) {
                    writeClientOutput(clientChannel, e.getMessage());
                } catch (NumberFormatException e) {
                    writeClientOutput(clientChannel, INVALID_AMOUNT_MESSAGE);
                }
            } else if (key.isAcceptable()) {
                accept(selector, key);
            }

            keyIterator.remove();
        }
    }

    private String getClientInput(SocketChannel clientChannel) throws IOException {
        buffer.clear();

        int readBytes = clientChannel.read(buffer);
        if (readBytes < MIN_BYTES_FOR_READS) {
            clientChannel.close();
            return null;
        }

        buffer.flip();

        byte[] clientInputBytes = new byte[buffer.remaining()];
        buffer.get(clientInputBytes);

        return new String(clientInputBytes, StandardCharsets.UTF_8);
    }

    private void writeClientOutput(SocketChannel clientChannel, String output) throws IOException {
        buffer.clear();
        buffer.put(output.getBytes());
        buffer.flip();

        clientChannel.write(buffer);
    }

    private void accept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();

        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
    }

}
