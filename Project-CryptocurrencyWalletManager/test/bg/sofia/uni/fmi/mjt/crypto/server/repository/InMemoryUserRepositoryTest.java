package bg.sofia.uni.fmi.mjt.crypto.server.repository;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.crypto.exceptions.UserExistsException;
import bg.sofia.uni.fmi.mjt.crypto.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryUserRepository();
    }


    @Test
    public void testAddUserWhenUsernameExist() throws UserExistsException {
        repository.addUser("username", "pass");

        assertThrows(UserExistsException.class, () -> repository.addUser("username", "password"),
            "When username exist in users repository UserExistsException should be thrown");
    }

    @Test
    public void testAddUser() throws UserExistsException {
        String result = "Register successful";
        assertTrue(repository.addUser("username", "password").contains(result),
            "When user was added successful returned message is incorrect");
    }

    @Test
    public void testLogInWhenUsernameDoesNotExist() {
        assertThrows(UserDoesNotExistsException.class, () -> repository.logIn("ivan", "ivan"),
        "When user does not exist in users repository UserDoesNotExistsException should be thrown");
    }

    @Test
    public void testLogInWithIncorrectPassword() throws UserExistsException, UserDoesNotExistsException {
        repository.addUser("ivan", "ivan");

        assertFalse(repository.logIn("ivan", "ivan1"),
            "When password is incorrect false should be returned");
    }

    @Test
    public void testLogInWithCorrectPassword() throws UserExistsException, UserDoesNotExistsException {
        repository.addUser("ivan", "ivan");

        assertTrue(repository.logIn("ivan", "ivan"),
            "When password is incorrect false should be returned");
    }

    @Test
    public void testGetUserWhenUsernameDoesNotExist() {
        assertThrows(UserDoesNotExistsException.class, () -> repository.getUser("ivan"),
            "When username does not exist in users repository, getUser should throw UserDoesNotExistsException");
    }

    @Test
    public void testGetUser() throws UserExistsException, UserDoesNotExistsException {
        User user = new User("Ivan", "Ivan");
        repository.addUser("Ivan", "Ivan");

        assertEquals(user, repository.getUser("Ivan"),
            "GetUser return incorrect user");
    }
}
