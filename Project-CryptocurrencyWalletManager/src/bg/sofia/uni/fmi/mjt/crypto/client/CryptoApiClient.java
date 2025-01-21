package bg.sofia.uni.fmi.mjt.crypto.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CryptoApiClient {

    private static final int SERVER_PORT = 8888;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = 1024;

    private static final ByteBuffer BUFFER = ByteBuffer.allocateDirect(BUFFER_SIZE);

    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            System.out.println("Welcome to the Cryptocurrency Wallet Manager!");
            while (true) {
                System.out.print("$ ");
                String message = scanner.nextLine();
                if (message.isEmpty()) {
                    System.out.println("Enter valid command!");
                    continue;
                }
                if ("exit".equals(message)) {
                    System.out.println("Disconnecting.");
                    break;
                }

                sendMessage(socketChannel, message);
                
                String reply = readMessage(socketChannel);
                System.out.println(reply);
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
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
