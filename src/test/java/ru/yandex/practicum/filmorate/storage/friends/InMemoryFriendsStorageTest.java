package ru.yandex.practicum.filmorate.storage.friends;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.user.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryFriendsStorageTest {
        private FriendsStorage friendsStorage;
        private UserStorage userStorage;
        private User correctUser;
        private User correctUser2;
        private int idUser;
        private int idUser2;

        @BeforeEach
        public void beforeEach() {
            userStorage = new InMemoryUserStorage(new GeneratorId());
            friendsStorage = new InMemoryFriendsStorage(userStorage);
            LocalDate localDate = LocalDate.of(2000, 3, 25);
            LocalDate localDate2 = LocalDate.of(2001, 3, 25);
            idUser = 1;
            idUser2 = 2;
            correctUser = User.builder()
                    .id(idUser)
                    .name("Nick Name")
                    .email("mail@mail.ru")
                    .login("dolore")
                    .birthday(localDate).build();
            correctUser2 = User.builder()
                    .id(idUser2)
                    .name("Nick Name")
                    .email("mail@yandex.ru")
                    .login("dolore")
                    .birthday(localDate2).build();
        }

    @Test
    void notHaveFriend() {
        userStorage.add(correctUser);
        assertFalse(friendsStorage.haveFriends(idUser));
    }

    @Test
    void addFriend() {
        userStorage.add(correctUser);
        userStorage.add(correctUser2);
        friendsStorage.addFriend(idUser, idUser2);
        assertTrue(friendsStorage.haveFriends(idUser));
        assertFalse(friendsStorage.haveFriends(idUser2));
    }

    @Test
    void removeFriend() {
        userStorage.add(correctUser);
        userStorage.add(correctUser2);
        friendsStorage.addFriend(idUser, idUser2);
        assertTrue(friendsStorage.haveFriends(idUser));
        friendsStorage.removeFriend(idUser, idUser2);
        assertFalse(friendsStorage.haveFriends(idUser));
    }

    @Test
    void getFriends() {
        userStorage.add(correctUser);
        userStorage.add(correctUser2);
        friendsStorage.addFriend(idUser, idUser2);
        assertEquals(1, friendsStorage.getFriends(idUser).size());
        assertEquals(0, friendsStorage.getFriends(idUser2).size());
    }
}