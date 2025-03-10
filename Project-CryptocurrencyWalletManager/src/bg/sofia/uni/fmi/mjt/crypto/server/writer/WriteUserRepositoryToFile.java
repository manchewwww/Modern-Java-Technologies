package bg.sofia.uni.fmi.mjt.crypto.server.writer;

import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class WriteUserRepositoryToFile {

    private static final Path PATH =
        Path.of("src", "bg", "sofia", "uni", "fmi", "mjt", "crypto", "server", "file", "data.json");
    private static final Gson GSON = new Gson();

    public static void writeData(UserRepository userRepository) throws IOException {
        File file = PATH.toFile();
        try (FileWriter fileWriter = new FileWriter(file.getAbsoluteFile())) {
            fileWriter.write(GSON.toJson(userRepository));
        } catch (IOException e) {
            throw new IOException(ErrorMessages.WRITE_USERS_ERROR);
        }
    }

}
