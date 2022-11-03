package ru.yandex.practicum.filmorate.server;

import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

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
        String name = getCorrectName(user);
        User creatingUser = user.toBuilder().id(generatorId.getId()).name(name).build();

        users.put(creatingUser.getId(), creatingUser);
        return creatingUser;
    }

    public boolean contains(User user) {
        return users.containsKey(user.getId());
    }

    public void updateUser(User user) {
        String name = getCorrectName(user);
        User creatingUser = user.toBuilder().name(name).build();

        users.put(user.getId(), creatingUser);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
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
