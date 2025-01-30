package bg.sofia.uni.fmi.mjt.crypto.server.hasher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        return ENCODER.encode(password);
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return ENCODER.matches(password, hashedPassword);
    }

}