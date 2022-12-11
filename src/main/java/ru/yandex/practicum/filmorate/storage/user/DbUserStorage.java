package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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
            return mapUser(userRows);
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
            int idUser = userRows.getInt("id");
            String nameUser = userRows.getString("name");
            String emailUser = userRows.getString("email");
            String loginUser = userRows.getString("login");
            LocalDate birthdayUser = userRows.getDate("birthday").toLocalDate();
            log.info("Найден пользователь: {} {}", idUser, nameUser);
            users.add(User.builder()
                    .id(idUser)
                    .name(nameUser)
                    .email(emailUser)
                    .login(loginUser)
                    .birthday(birthdayUser)
                    .build());
        }
        return users;
    }

    @Override
    public User get(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?;";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, userId);

        if (userRows.next()) {
            int idUser = userRows.getInt("id");
            String nameUser = userRows.getString("name");
            String emailUser = userRows.getString("email");
            String loginUser = userRows.getString("login");
            LocalDate birthdayUser = userRows.getDate("birthday").toLocalDate();
            log.info("Найден пользователь: {} {}", idUser, nameUser);
            return User.builder()
                    .id(idUser)
                    .name(nameUser)
                    .email(emailUser)
                    .login(loginUser)
                    .birthday(birthdayUser)
                    .build();
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
            int idUser = userRows.getInt("id");
            String nameUser = userRows.getString("name");
            log.info("Найден пользователь: {} {}", idUser, nameUser);
            return true;
        } else {
            log.info("Пользователь с идентификатором {} не найден.", userId);
            return false;
        }
    }

    @Override
    @Modifying
    public boolean addFriend(Integer userId, Integer friendId) {
        String sql = "INSERT INTO friends_user " +
                "(id_first_user, id_second_user) " +
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

    @Override
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

    private User mapUser(SqlRowSet userRows) {
        int idUser = userRows.getInt("id");
        String nameUser = userRows.getString("name");
        String emailUser = userRows.getString("email");
        String loginUser = userRows.getString("login");
        LocalDate birthdayUser = userRows.getDate("birthday").toLocalDate();
        log.info("Найден пользователь: {} {}", idUser, nameUser);
        return User.builder()
                .id(idUser)
                .name(nameUser)
                .email(emailUser)
                .login(loginUser)
                .birthday(birthdayUser)
                .build();
    }
}
