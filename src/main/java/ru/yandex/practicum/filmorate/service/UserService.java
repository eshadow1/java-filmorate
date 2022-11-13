package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
        String name = getCorrectName(user);
        User creatingUser = user.toBuilder().name(name).build();
        userStorage.update(creatingUser);
    }

    public User getUser(int userId) {
        if(!userStorage.contains(userId)) {
            throw new ContainsException("Id" + userId + " пользователя не найдено");
        }
        return userStorage.get(userId);
    }


    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    public void addFriend(Integer userId, Integer friendId) {
        if (!userStorage.contains(userId) || !userStorage.contains(friendId)) {
            throw new ContainsException("Id" + userId + " пользователя не найдено");
        }
        userStorage.addFriend(userId, friendId);
        userStorage.addFriend(friendId, userId);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        if (!userStorage.contains(userId) || !userStorage.contains(friendId)) {
            throw new ContainsException("Id" + userId + " пользователя не найдено");
        }

        if (userStorage.haveFriend(userId)) {
            userStorage.removeFriend(userId, friendId);
        }

        if (userStorage.haveFriend(friendId)) {
            userStorage.removeFriend(friendId, userId);
        }
    }

    public List<User> getFriends(Integer userId) {
        if(!userStorage.contains(userId)) {
            throw new ContainsException("Id" + userId + " пользователя не найдено");
        }

        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        if(!userStorage.contains(userId) || !userStorage.contains(otherId)) {
            throw new ContainsException("Id пользователей не найдено");
        }

        var userFriends = userStorage.getFriends(userId);
        var otherFriends = userStorage.getFriends(otherId);
        userFriends.addAll(otherFriends);
        var temp = new HashSet<>(userFriends);
        temp.remove(userStorage.get(userId));
        temp.remove(userStorage.get(otherId));
        return new ArrayList<>(temp);
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


}
