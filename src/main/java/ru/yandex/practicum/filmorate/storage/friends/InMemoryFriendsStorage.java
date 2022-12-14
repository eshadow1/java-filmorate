package ru.yandex.practicum.filmorate.storage.friends;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.user.User;
import ru.yandex.practicum.filmorate.models.user.Users;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Qualifier("inMemory")
public class InMemoryFriendsStorage implements FriendsStorage{
    private final Map<Integer, Set<Integer>> usersFriends;
    private final UserStorage userStorage;

    public InMemoryFriendsStorage(@Qualifier("inMemory") UserStorage userStorage) {
        this.usersFriends = new HashMap<>();
        this.userStorage = userStorage;
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
                .map(id -> userStorage.contains(id) ? userStorage.get(id) : Users.getRemoveUser(id))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<User> getCommonFriends(Integer firstUserId, Integer secondUserId) {
        var userFriends = getFriends(firstUserId);
        var otherUserFriends = getFriends(secondUserId);

        return userFriends.stream()
                .filter(otherUserFriends::contains)
                .filter(user -> user.getId() != firstUserId)
                .filter(user -> user.getId() != secondUserId)
                .collect(Collectors.toSet());
    }

    @Override
    public void removeUser(User user) {
        usersFriends.remove(user.getId());
        userStorage.remove(user);
    }
}
