package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.user.User;
import ru.yandex.practicum.filmorate.models.user.Users;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Qualifier("inMemory")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users;
    private final Map<Integer, Set<Integer>> usersFriends;

    public InMemoryUserStorage() {
        this.users = new HashMap<>();
        this.usersFriends = new HashMap<>();
    }

    @Override
    public User add(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User remove(User user) {
        usersFriends.remove(user.getId());
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

    @Override
    public boolean addFriend(Integer userId, Integer friendId) {
        if (!usersFriends.containsKey(userId)) {
            usersFriends.put(userId, new HashSet<>());
        }
        usersFriends.get(userId).add(friendId);
        return true;
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        usersFriends.get(userId).remove(friendId);
    }

    @Override
    public boolean haveFriends(Integer userId) {
        var userFriends = usersFriends.get(userId);

        if (userFriends == null) {
            return false;
        }
        return !userFriends.isEmpty();
    }

    @Override
    public Set<User> getFriends(Integer userId) {
        var userFriends = usersFriends.get(userId);

        if (userFriends == null) {
            return Collections.emptySet();
        }

        return userFriends.stream()
                .map(id -> users.containsKey(id) ? users.get(id) : Users.getRemoveUser(id))
                .collect(Collectors.toSet());
    }
}
