package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.models.user.User;
import ru.yandex.practicum.filmorate.storage.Storage;

public interface UserStorage extends UserFriendsStorage, Storage<User> {
}
