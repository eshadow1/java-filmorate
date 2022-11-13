package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface UserFriendsStorage {
    boolean addFriend(Integer userId, Integer friendId);
    void removeFriend(Integer userId, Integer friendId);
    boolean haveFriend(Integer userId);
    List<User> getFriends(Integer userId);
}
