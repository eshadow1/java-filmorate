package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final GeneratorId generatorId;

    public UserService(UserStorage userStorage, GeneratorId generatorId) {
        this.userStorage = userStorage;
        this.generatorId = generatorId;
    }

    public User addUser(User user) {
        String name = getCorrectName(user);
        User creatingUser = user.toBuilder().id(generatorId.getId()).name(name).build();
        return userStorage.add(creatingUser);
    }

    public boolean contains(User user) {
        return userStorage.contains(user.getId());
    }

    public boolean contains(int id) {
        return userStorage.contains(id);
    }

    public void updateUser(User user) {
        checkedUserContains(user.getId());

        String name = getCorrectName(user);
        User creatingUser = user.toBuilder().name(name).build();
        userStorage.update(creatingUser);
    }

    public User getUser(int userId) {
        checkedUserContains(userId);

        return userStorage.get(userId);
    }


    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    public void addFriend(Integer userId, Integer friendId) {
        checkedUserContains(userId);
        checkedUserContains(friendId);

        userStorage.addFriend(userId, friendId);
        userStorage.addFriend(friendId, userId);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        checkedUserContains(userId);
        checkedUserContains(friendId);

        if (userStorage.haveFriends(userId)) {
            userStorage.removeFriend(userId, friendId);
        }

        if (userStorage.haveFriends(friendId)) {
            userStorage.removeFriend(friendId, userId);
        }
    }

    public List<User> getFriends(Integer userId) {
        checkedUserContains(userId);

        return new ArrayList<>(userStorage.getFriends(userId));
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        checkedUserContains(userId);
        checkedUserContains(otherId);

        var userFriends = userStorage.getFriends(userId);
        var otherUserFriends = userStorage.getFriends(otherId);

        return userFriends.stream().filter(otherUserFriends::contains).collect(Collectors.toList());
    }

    public static boolean isEmptyName(String name) {
        return name == null || name.isEmpty();
    }

    private String getCorrectName(User user) {
        if (isEmptyName(user.getName())) {
            return user.getLogin();
        }

        return user.getName();
    }

    private void checkedUserContains(int id) {
        if (!userStorage.contains(id)) {
            throw new ContainsException("Пользователь с id " + id + " не найден");
        }
    }
}
