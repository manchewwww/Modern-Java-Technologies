package bg.sofia.uni.fmi.mjt.poll.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class PollClient {

    private static final int SERVER_PORT = 8888;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = 512;

    private static ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

    public void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            System.out.println("Connected to the server.");
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine(); // read a line from the console
                if (message.isBlank()) {
                    System.out.println("{\"status\":\"ERROR\",\"message\":\"Invalid command\"}");
                    continue;
                }
                System.out.println(message);
                if ("disconnect".equals(message)) {
                    break;
                }

                this.actionsWithBuffer(socketChannel, message);

                byte[] byteArray = new byte[buffer.remaining()];
                buffer.get(byteArray);
                String reply = new String(byteArray, "UTF-8"); // buffer drain
                System.out.println(reply);
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }

    private void actionsWithBuffer(SocketChannel socketChannel, String message) throws IOException {
        buffer.clear(); // switch to writing mode
        buffer.put(message.getBytes()); // buffer fill
        buffer.flip(); // switch to reading mode
        socketChannel.write(buffer); // buffer drain
        buffer.clear(); // switch to writing mode
        socketChannel.read(buffer); // buffer fill
        buffer.flip(); // switch to reading mode
    }

}
