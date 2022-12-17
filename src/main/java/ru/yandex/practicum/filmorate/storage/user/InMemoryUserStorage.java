package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.user.User;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.util.*;

@Repository
@Qualifier("inMemory")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users;
    private final GeneratorId generatorId;

    public InMemoryUserStorage(GeneratorId generatorId) {
        this.users = new HashMap<>();
        this.generatorId = generatorId;
    }

    @Override
    public User add(User user) {
        User creatingUser = user.toBuilder().id(generatorId.getId()).build();
        users.put(creatingUser.getId(), creatingUser);
        return creatingUser;
    }

    @Override
    public User remove(User user) {
        return users.remove(user.getId());
    }

    @Override
    public User update(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User get(int idUser) {
        return users.get(idUser);
    }

    @Override
    public boolean contains(int userId) {
        return users.containsKey(userId);
    }

}
