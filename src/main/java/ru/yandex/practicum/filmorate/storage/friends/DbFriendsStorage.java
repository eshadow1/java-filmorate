package ru.yandex.practicum.filmorate.storage.friends;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.user.User;

import java.util.HashSet;
import java.util.Set;

import static ru.yandex.practicum.filmorate.mapping.Mapping.mapUser;

@Repository
@Qualifier("inDb")
public class DbFriendsStorage implements FriendsStorage {
    private final JdbcTemplate jdbcTemplate;

    public DbFriendsStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Modifying
    public boolean addFriend(Integer userId, Integer friendId) {
        String sql = "INSERT INTO friends_user (id_first_user, id_second_user) " +
                "VALUES (?, ?);";
        jdbcTemplate.update(sql, userId, friendId);
        return true;
    }

    @Override
    @Modifying
    public void removeFriend(Integer userId, Integer friendId) {
        String sql = "DELETE FROM friends_user " +
                "WHERE id_first_user = ? " +
                "and id_second_user = ?;";

        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public boolean haveFriends(Integer userId) {
        String sql = "SELECT * " +
                "FROM friends_user " +
                "WHERE id_first_user = ?;";

        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, userId);

        return userRows.next();
    }

    public Set<User> getFriends(Integer userId) {
        String sql = "SELECT * " +
                "FROM users " +
                "WHERE id IN " +
                "(SELECT id_second_user " +
                "FROM friends_user " +
                "WHERE id_first_user = ?);";

        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, userId);
        Set<User> users = new HashSet<>();

        while (userRows.next()) {
            users.add(mapUser(userRows));
        }
        return users;
    }

    @Override
    public Set<User> getCommonFriends(Integer firstUserId, Integer secondUserId) {
        String sql = "SELECT * " +
                "FROM users " +
                "WHERE id IN " +
                "(SELECT id_second_user " +
                "FROM friends_user " +
                "WHERE id_first_user IN (?, ?) " +
                "AND id_second_user NOT IN (?, ?));";

        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql,
                firstUserId, secondUserId,
                firstUserId, secondUserId);
        Set<User> users = new HashSet<>();

        while (userRows.next()) {
            users.add(mapUser(userRows));
        }
        return users;
    }

    @Override
    @Modifying
    public void removeUser(User userId) {
        String sql = "DELETE FROM friends_user " +
                "WHERE id_first_user = ?; " +
                "DELETE FROM users " +
                "WHERE id = ?;";

        jdbcTemplate.update(sql, userId.getId());
    }

}
