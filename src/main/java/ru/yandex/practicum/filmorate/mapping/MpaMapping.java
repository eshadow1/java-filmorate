package ru.yandex.practicum.filmorate.mapping;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.exception.BadDataException;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;

public class MpaMapping {
    static public Mpa map(SqlRowSet mpaRow) {
        try {
            int idInDb = mpaRow.getInt("id");
            String nameInDb = mpaRow.getString("name");
            return Mpa.builder()
                    .id(idInDb)
                    .name(nameInDb)
                    .build();
        } catch (Exception error) {
            throw new BadDataException("Ошибка в данных рейтинга MPA:" + error.getMessage());
        }
    }
}
