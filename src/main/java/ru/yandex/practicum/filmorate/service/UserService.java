package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

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
        return userStorage.contains(user);
    }

    public void updateUser(User user) {
        String name = getCorrectName(user);
        User creatingUser = user.toBuilder().name(name).build();
        userStorage.update(creatingUser);
    }

    public List<User> getAllUsers() {
        return userStorage.getAll();
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
