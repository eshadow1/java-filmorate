package ru.yandex.practicum.filmorate.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserServerTest {
    private UserServer userServer;
    private User correctUser;
    private User nullNameUser;
    private User updateUser;

    @BeforeEach
    public void beforeEach() {
        userServer = new UserServer();
        LocalDate localDate = LocalDate.of(2000, 3, 25);
        correctUser = User.builder()
                .id(0)
                .name("Nick Name")
                .email("mail@mail.ru")
                .login("dolore")
                .birthday(localDate).build();
        nullNameUser = User.builder()
                .id(0)
                .name(null)
                .email("mail@mail.ru")
                .login("dolore")
                .birthday(localDate).build();
        updateUser = User.builder()
                .id(1)
                .name("Nick")
                .email("mail@mail.ru")
                .login("dolore")
                .birthday(localDate).build();
    }

    @Test
    void addUser() {
        userServer.addUser(correctUser);
        assertEquals(1, userServer.getAllUsers().size());
    }
    @Test
    void addNullNameUser() {
        userServer.addUser(nullNameUser);
        assertEquals("dolore", userServer.getAllUsers().get(0).getName());
    }

    @Test
    void containsCorrect() {
        var film = userServer.addUser(correctUser);
        assertTrue(userServer.contains(film));
    }

    @Test
    void containsIncorrect() {
        userServer.addUser(correctUser);
        assertFalse(userServer.contains(correctUser));
    }

    @Test
    void updateUser() {
        userServer.addUser(correctUser);
        userServer.updateUser(updateUser);
        assertEquals("Nick", userServer.getAllUsers().get(0).getName());
    }

    @Test
    void getAllUsers() {
        userServer.addUser(correctUser);
        assertEquals(1, userServer.getAllUsers().size());
    }
}