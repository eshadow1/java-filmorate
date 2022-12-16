package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.user.User;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.filmorate.mapping.UserMapping.map;

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
    public User add(User user) {
        String sql = "INSERT INTO users " +
                "(email, login, name, birthday) " +
                "VALUES (?, ?, ?, ?);";

        jdbcTemplate.update(sql, user.getEmail(),
                user.getLogin(), user.getName(), user.getBirthday());

        String sqlUser = "SELECT * " +
                "FROM users " +
                "WHERE email = ? AND " +
                "login = ? AND " +
                "name = ? AND " +
                "birthday = ?;";

        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sqlUser, user.getEmail(),
                user.getLogin(), user.getName(), user.getBirthday());

        if (userRows.next()) {
            return map(userRows);
        }
        return null;
    }

    @Override
    @Modifying
    public User remove(User user) {
        String sql = "DELETE FROM users " +
                "WHERE id = ?;";

        jdbcTemplate.update(sql, user.getId());
        return user;
    }

    @Override
    @Modifying
    public User update(User user) {
        String sql = "UPDATE users " +
                "SET name = ?, " +
                "email = ?, " +
                "login = ?, " +
                "birthday = ? " +
                "WHERE id = ?;";

        jdbcTemplate.update(sql, user.getName(),
                user.getEmail(), user.getLogin(),
                user.getBirthday(), user.getId());

        return get(user.getId());
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users;";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql);
        List<User> users = new ArrayList<>();

        while (userRows.next()) {
            logInfoFindUser(userRows);
            users.add(map(userRows));
        }
        return users;
    }

    @Override
    public User get(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?;";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, userId);

        if (userRows.next()) {
            logInfoFindUser(userRows);
            return map(userRows);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", userId);
            return null;
        }
    }

    @Override
    public boolean contains(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?;";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, userId);

        if (userRows.next()) {
            logInfoFindUser(userRows);
            return true;
        } else {
            log.info("Пользователь с идентификатором {} не найден.", userId);
            return false;
        }
    }

    private void logInfoFindUser(SqlRowSet userRows) {
        int idUser = userRows.getInt("id");
        String nameUser = userRows.getString("name");
        log.info("Найден пользователь: {} {}", idUser, nameUser);
    }
}
