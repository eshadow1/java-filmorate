package ru.yandex.practicum.filmorate.mapping;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.models.user.User;

import java.time.LocalDate;

public class Mapping {
    static public User mapUser(SqlRowSet userRows) {
        int idUser = userRows.getInt("id");
        String nameUser = userRows.getString("name");
        String emailUser = userRows.getString("email");
        String loginUser = userRows.getString("login");
        LocalDate birthdayUser = userRows.getDate("birthday").toLocalDate();
        return User.builder()
                .id(idUser)
                .name(nameUser)
                .email(emailUser)
                .login(loginUser)
                .birthday(birthdayUser)
                .build();
    }
}
