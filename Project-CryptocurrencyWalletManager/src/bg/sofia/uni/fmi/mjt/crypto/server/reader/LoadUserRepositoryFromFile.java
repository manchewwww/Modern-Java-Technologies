package bg.sofia.uni.fmi.mjt.crypto.server.reader;

import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.InMemoryUserRepository;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class LoadUserRepositoryFromFile {

    private static final Path PATH =
        Path.of("src", "bg", "sofia", "uni", "fmi", "mjt", "crypto", "server", "file", "data.json");
    private static final Gson GSON = new Gson();

    public static UserRepository loadData() {
        File file = PATH.toFile();
        if (!file.exists()) {
            return new InMemoryUserRepository();
        }
        try (FileReader fileWriter = new FileReader(PATH.toFile())) {
            return GSON.fromJson(fileWriter, InMemoryUserRepository.class);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessages.LOAD_USERS_ERROR);
        }
    }

}
