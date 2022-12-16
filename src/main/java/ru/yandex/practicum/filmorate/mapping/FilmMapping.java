package ru.yandex.practicum.filmorate.mapping;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.exception.BadDataException;
import ru.yandex.practicum.filmorate.models.film.Film;
import ru.yandex.practicum.filmorate.models.genre.Genre;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;

import java.time.LocalDate;
import java.util.Set;

public class FilmMapping {
    static public Film map(SqlRowSet filmRow, Set<Genre> genres) {
        try {
            int idFilm = filmRow.getInt("ID");
            String nameFilm = filmRow.getString("NAME");
            String descriptionFilm = filmRow.getString("DESCRIPTION");
            LocalDate releaseDateFilm = filmRow.getDate("RELEASE_DATE").toLocalDate();
            int durationFilm = filmRow.getInt("DURATION");

            int mpaId = filmRow.getInt("ID_MPA");
            String mpaName = filmRow.getString("MPA_NAME");
            Mpa mpa = Mpa.builder().id(mpaId).name(mpaName).build();

            return Film.builder()
                    .id(idFilm)
                    .name(nameFilm)
                    .description(descriptionFilm)
                    .releaseDate(releaseDateFilm)
                    .duration(durationFilm)
                    .mpa(mpa)
                    .genres(genres)
                    .build();
        } catch (Exception error) {
            throw new BadDataException("Ошибка в данных фильма:" + error.getMessage());
        }
    }
}
