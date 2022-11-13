package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface UserStorage extends UserFriendsStorage {
    User add(User user);
    User remove(User user);
    User update(User user);
    List<User> getAll();
    User get(Integer idUser);
    boolean contains(int userId);
}
