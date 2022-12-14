package ru.yandex.practicum.filmorate.storage.friends;

import ru.yandex.practicum.filmorate.models.user.User;

import java.util.Set;

public interface FriendsStorage {
    boolean addFriend(Integer userId, Integer friendId);

    void removeFriend(Integer userId, Integer friendId);

    boolean haveFriends(Integer userId);

    Set<User> getFriends(Integer userId);

    Set<User> getCommonFriends(Integer firstUserId, Integer secondUserId);

    void removeUser(User userId);
}
