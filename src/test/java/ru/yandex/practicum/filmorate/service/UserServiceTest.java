package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private User correctUser;
    private User nullNameUser;
    private User updateUser;

    @BeforeEach
    public void beforeEach() {
        userService = new UserService(new InMemoryUserStorage(), new GeneratorId());
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
        userService.addUser(correctUser);
        assertEquals(1, userService.getAllUsers().size());
    }
    @Test
    void addNullNameUser() {
        userService.addUser(nullNameUser);
        assertEquals("dolore", userService.getAllUsers().get(0).getName());
    }

    @Test
    void containsCorrect() {
        var film = userService.addUser(correctUser);
        assertTrue(userService.contains(film));
    }

    @Test
    void containsIncorrect() {
        userService.addUser(correctUser);
        assertFalse(userService.contains(correctUser));
    }

    @Test
    void updateUser() {
        userService.addUser(correctUser);
        userService.updateUser(updateUser);
        assertEquals("Nick", userService.getAllUsers().get(0).getName());
    }

    @Test
    void getAllUsers() {
        userService.addUser(correctUser);
        assertEquals(1, userService.getAllUsers().size());
    }
}