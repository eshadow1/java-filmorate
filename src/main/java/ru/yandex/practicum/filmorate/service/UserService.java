package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.models.user.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public UserService(@Qualifier("inDb") UserStorage userStorage,
    @Qualifier("inDb") FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    public User addUser(User user) {
        String name = getCorrectName(user);
        User creatingUser = user.toBuilder().name(name).build();
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

        friendsStorage.addFriend(userId, friendId);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        checkedUserContains(userId);
        checkedUserContains(friendId);

        if (friendsStorage.haveFriends(userId)) {
            friendsStorage.removeFriend(userId, friendId);
        }
    }

    public List<User> getFriends(Integer userId) {
        checkedUserContains(userId);

        return friendsStorage.getFriends(userId).stream()
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        checkedUserContains(userId);
        checkedUserContains(otherId);

        var commonFriends = friendsStorage.getCommonFriends(userId, otherId);

        return commonFriends.stream()
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
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
            throw new ContainsException("???????????????????????? ?? id " + id + " ???? ????????????");
        }
    }
}
