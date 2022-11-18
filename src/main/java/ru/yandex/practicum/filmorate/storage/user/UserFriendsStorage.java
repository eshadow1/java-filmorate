package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.models.User;

import java.util.Set;

public interface UserFriendsStorage {
    boolean addFriend(Integer userId, Integer friendId);

    void removeFriend(Integer userId, Integer friendId);

    boolean haveFriends(Integer userId);

    Set<User> getFriends(Integer userId);
}
