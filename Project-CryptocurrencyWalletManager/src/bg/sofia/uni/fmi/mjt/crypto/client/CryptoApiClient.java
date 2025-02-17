package bg.sofia.uni.fmi.mjt.crypto.client;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CryptoApiClient {

    private static final String WELCOME_MESSAGE = "Welcome to the Cryptocurrency Wallet Manager!";
    private static final String LINE_SYMBOL = "$ ";
    private static final String DISCONNECTING_MESSAGE = "Disconnecting.";
    private static final String PROBLEM_WITH_COMMUNICATION_MESSAGE
        = "There is a problem with the network communication";
    private static final String CLIENT_STOP_MESSAGE = "exit";
    private static final String INVALID_COMMAND_MESSAGE = "Invalid command!";

    private static final int SERVER_PORT = 8888;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = 102400000;

    private static final ByteBuffer BUFFER = ByteBuffer.allocateDirect(BUFFER_SIZE);

    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner commandInput = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            System.out.println(WELCOME_MESSAGE);
            while (true) {
                System.out.print(LINE_SYMBOL);
                String message = commandInput.nextLine();
                if (message.isEmpty()) {
                    System.out.println(INVALID_COMMAND_MESSAGE);
                    continue;
                }
                if (CLIENT_STOP_MESSAGE.equals(message)) {
                    System.out.println(DISCONNECTING_MESSAGE);
                    break;
                }

                sendMessage(socketChannel, message);
                String reply = readMessage(socketChannel);

                System.out.println(reply);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(PROBLEM_WITH_COMMUNICATION_MESSAGE, e);
        }
    }

    private static void sendMessage(SocketChannel socketChannel, String message) throws IOException {
        BUFFER.clear(); // switch to writing mode
        BUFFER.put(message.getBytes()); // buffer fill
        BUFFER.flip(); // switch to reading mode
        socketChannel.write(BUFFER); // buffer drain
    }

    private static String readMessage(SocketChannel socketChannel) throws IOException {
        BUFFER.clear(); // switch to writing mode
        socketChannel.read(BUFFER); // buffer fill
        BUFFER.flip(); // switch to reading mode

        byte[] byteArray = new byte[BUFFER.remaining()];
        BUFFER.get(byteArray);

        return new String(byteArray, StandardCharsets.UTF_8);
    }

}
