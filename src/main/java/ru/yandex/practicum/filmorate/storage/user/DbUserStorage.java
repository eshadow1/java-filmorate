package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.user.User;

import java.util.List;
import java.util.Set;

@Repository
@Qualifier("inDb")
public class DbUserStorage implements UserStorage {
    @Override
    public User add(User object) {
        return null;
    }

    @Override
    public User remove(User object) {
        return null;
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User get(int objectId) {
        return null;
    }

    @Override
    public boolean contains(int objectId) {
        return false;
    }

    @Override
    public boolean addFriend(Integer userId, Integer friendId) {
        return false;
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {

    }

    @Override
    public boolean haveFriends(Integer userId) {
        return false;
    }

    @Override
    public Set<User> getFriends(Integer userId) {
        return null;
    }
}
