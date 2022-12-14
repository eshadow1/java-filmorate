package ru.yandex.practicum.filmorate.storage.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.user.User;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserStorageTest {
    private UserStorage userStorage;
    private User correctUser;
    private User correctUser2;
    private User updateUser;
    private int idUser;
    private int idUser2;

    @BeforeEach
    public void beforeEach() {
        userStorage = new InMemoryUserStorage(new GeneratorId());
        LocalDate localDate = LocalDate.of(2000, 3, 25);
        LocalDate localDate2 = LocalDate.of(2001, 3, 25);
        idUser = 1;
        idUser2 = 1;
        correctUser = User.builder()
                .id(idUser)
                .name("Nick Name")
                .email("mail@mail.ru")
                .login("dolore")
                .birthday(localDate).build();
        correctUser2 = User.builder()
                .id(idUser)
                .name("Nick Name")
                .email("mail@yandex.ru")
                .login("dolore")
                .birthday(localDate2).build();
        updateUser = User.builder()
                .id(idUser)
                .name("Nick")
                .email("mail@mail.ru")
                .login("dolore")
                .birthday(localDate).build();
    }

    @Test
    void add() {
        userStorage.add(correctUser);
        assertTrue(userStorage.contains(correctUser.getId()));
        assertEquals(correctUser, userStorage.get(correctUser.getId()));
    }

    @Test
    void remove() {
        userStorage.add(correctUser);
        userStorage.remove(correctUser);
        assertFalse(userStorage.contains(correctUser.getId()));
        assertNull(userStorage.get(correctUser.getId()));
    }

    @Test
    void update() {
        userStorage.add(correctUser);
        userStorage.update(updateUser);
        assertEquals("Nick", userStorage.get(correctUser.getId()).getName());
    }

    @Test
    void getAll() {
        userStorage.add(correctUser);
        assertEquals(1, userStorage.getAll().size());
    }
}