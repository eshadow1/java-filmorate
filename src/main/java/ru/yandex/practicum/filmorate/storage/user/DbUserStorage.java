package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.user.User;

import java.util.List;
import java.util.Set;

@Repository
@Qualifier("inDb")
public class DbUserStorage implements UserStorage {
    private final Logger log = LoggerFactory.getLogger(DbUserStorage.class);
    private final JdbcTemplate jdbcTemplate;

    public DbUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Modifying
    public User add(User object) {
        return null;
    }

    @Override
    @Modifying
    public User remove(User object) {
        return null;
    }

    @Override
    @Modifying
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
    @Modifying
    public boolean addFriend(Integer userId, Integer friendId) {
        return false;
    }

    @Override
    @Modifying
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
