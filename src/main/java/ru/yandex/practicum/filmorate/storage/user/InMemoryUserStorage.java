package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
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
    public User get(Integer idUser) {
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
    public boolean haveFriend(Integer userId) {
        if (!usersFriends.containsKey(userId)) {
            return false;
        }
        return !usersFriends.get(userId).isEmpty();
    }

    @Override
    public List<User> getFriends(Integer userId) {
        if (!usersFriends.containsKey(userId)) {
            return Collections.emptyList();
        }

        return usersFriends.get(userId).stream().map(id ->
                users.containsKey(id) ? users.get(id) :
                        User.builder()
                                .id(id)
                                .login("removeUser")
                                .name("removeUser").build()
        ).collect(Collectors.toList());
    }
}
