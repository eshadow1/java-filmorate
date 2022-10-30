package ru.yandex.practicum.filmorate.server;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.utils.GeneratorId;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServer {
    private final Map<Integer, User> users;
    private final GeneratorId generatorId;

    public UserServer() {
        this.users = new HashMap<>();
        this.generatorId = new GeneratorId();
    }

    public User addUser(User user) {
        if (!Validator.validFeatureDate(user.getBirthday())) {
            throw new ValidationException("Некорректная дата дня рождения");
        }
        String name;
        if (user.getName() == null || user.getName().isEmpty()) {
            name = user.getLogin();
        } else {
            name = user.getName();
        }
        User creatingUser = user.toBuilder().id(generatorId.getId()).name(name).build();
        users.put(creatingUser.getId(), creatingUser);
        return creatingUser;
    }

    public boolean contains(User user) {
        return users.containsKey(user.getId());
    }

    public User updateUser(User user) {
        if (!Validator.validFeatureDate(user.getBirthday())) {
            throw new ValidationException("Некорректная дата дня рождения");
        }
        String name = user.getName().isEmpty() ? user.getLogin() : user.getName();
        User creatingUser = user.toBuilder().name(name).build();

        return users.replace(user.getId(), creatingUser);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
