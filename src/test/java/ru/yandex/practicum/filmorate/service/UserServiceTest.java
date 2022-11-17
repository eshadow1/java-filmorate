package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private User correctUser;
    private User correctUser2;
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
        correctUser2 = User.builder()
                .id(0)
                .name("Nick Name")
                .email("mail@yandex.ru")
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
    void containsIdCorrect() {
        var film = userService.addUser(correctUser);
        assertTrue(userService.contains(film.getId()));
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

    @Test
    void getUser() {
        userService.addUser(correctUser);
        assertEquals(1, userService.getUser(1).getId());
    }

    @Test
    void getNoAddedUser() {
        assertThrows(
                ContainsException.class,
                () -> userService.getUser(1));
    }

    @Test
    void addNotFriend() {
        userService.addUser(correctUser);
        assertThrows(
                ContainsException.class,
                () -> userService.addFriend(1, -1));
    }

    @Test
    void getFriend() {
        userService.addUser(correctUser);
        userService.addUser(correctUser2);
        userService.addFriend(1, 2);
        assertEquals(1, userService.getFriends(1).size());
        assertEquals(1, userService.getFriends(2).size());
    }

    @Test
    void getNotUserFriend() {
        assertThrows(
                ContainsException.class,
                () -> userService.getFriends(1));
    }

    @Test
    void getCommonFriend() {
        LocalDate localDate = LocalDate.of(2002, 1, 25);
        User correctUser3 = User.builder()
                .id(0)
                .name("Nick Name")
                .email("mail@yandex.ru")
                .login("dolore")
                .birthday(localDate).build();
        User correctUser4 = User.builder()
                .id(0)
                .name("Nick Name")
                .email("ya@yandex.ru")
                .login("dol")
                .birthday(localDate).build();

        userService.addUser(correctUser);
        userService.addUser(correctUser2);
        userService.addUser(correctUser3);
        userService.addUser(correctUser4);
        userService.addFriend(1, 3);
        userService.addFriend(1, 4);
        userService.addFriend(2, 3);
        userService.addFriend(2, 1);

        assertEquals(1, userService.getCommonFriends(1, 2).size());
        assertEquals(3, userService.getCommonFriends(1, 2).get(0).getId());
    }

    @Test
    void getCommonUnknownFriend() {
        userService.addUser(correctUser);
        userService.addUser(correctUser2);
        assertThrows(
                ContainsException.class,
                () -> userService.getCommonFriends(1, 4));
    }

    @Test
    void addNotUserFriend() {
        userService.addUser(correctUser);
        assertThrows(
                ContainsException.class,
                () -> userService.addFriend(-1, 1));
    }

    @Test
    void removeFriend() {
        userService.addUser(correctUser);
        userService.addUser(correctUser2);
        userService.addFriend(1, 2);
        userService.removeFriend(2, 1);
        assertEquals(0, userService.getFriends(1).size());
        assertEquals(0, userService.getFriends(2).size());
    }

    @Test
    void removeNotFriend() {
        userService.addUser(correctUser);
        assertThrows(
                ContainsException.class,
                () -> userService.removeFriend(1, -1));
    }

    @Test
    void removeNotUserFriend() {
        userService.addUser(correctUser);
        assertThrows(
                ContainsException.class,
                () -> userService.removeFriend(-1, 1));
    }
}