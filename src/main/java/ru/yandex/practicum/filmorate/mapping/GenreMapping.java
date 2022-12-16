package ru.yandex.practicum.filmorate.mapping;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.exception.BadDataException;
import ru.yandex.practicum.filmorate.models.genre.Genre;

public class GenreMapping {
    static public Genre map(SqlRowSet genreRow) {
        try {
            int idInDb = genreRow.getInt("id");
            String nameInDb = genreRow.getString("name");
            return Genre.builder()
                    .id(idInDb)
                    .name(nameInDb)
                    .build();
        } catch (Exception error) {
            throw new BadDataException("Ошибка в данных жанра:" + error.getMessage());
        }
    }
}
