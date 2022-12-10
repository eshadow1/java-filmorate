package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.film.Film;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("inDb")
public class DbFilmStorage implements FilmStorage{
    private final Logger log = LoggerFactory.getLogger(DbFilmStorage.class);
    private final JdbcTemplate jdbcTemplate;

    public DbFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Modifying
    public Film add(Film film) {
        String sql = "INSERT INTO films " +
                "(name, description, release_date, duration, id_mpa) " +
                "VALUES (?, ?, ?, ?, ?)";

        int res = jdbcTemplate.update(sql, film.getName(),
                film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId());

        return film;
    }

    @Override
    @Modifying
    public Film remove(Film film) {
        String sql = "DELETE FROM films " +
                "WHERE id = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, film.getId());
        return null;
    }

    @Override
    @Modifying
    public Film update(Film film) {
        String sql = "UPDATE films " +
                "SET name = ?, " +
                "description = ?, " +
                "release_date = ?, " +
                "duration = ?, " +
                "id_mpa = ? " +
                "WHERE id = ?";

       int res = jdbcTemplate.update(sql, film.getName(),
                film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());
        return film;
    }

    @Override
    public List<Film> getAll() {
        String sql = "select * from films";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql);
        List<Film> films = new ArrayList<>();

        while (userRows.next()) {
            int idFilm = userRows.getInt("id");
            String nameFilm = userRows.getString("name");
            String descriptionFilm = userRows.getString("description");
            LocalDate releaseDateFilm = userRows.getDate("release_date").toLocalDate();
            int durationFilm = userRows.getInt("duration");
            int mpaId = userRows.getInt("id_mpa");
            Mpa mpa = new Mpa();
            mpa.setId(mpaId);
            log.info("Найден фильм: {} {}", idFilm, nameFilm);
            films.add(Film.builder()
                    .id(idFilm)
                    .name(nameFilm)
                    .description(descriptionFilm)
                    .releaseDate(releaseDateFilm)
                    .duration(durationFilm)
                    .mpa(mpa)
                    .build());
        }
        return films;
    }

    @Override
    public Film get(int filmId) {
        String sql = "select * from genre where id = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, filmId);

        if (userRows.next()) {
            int idFilm = userRows.getInt("id");
            String nameFilm = userRows.getString("name");
            String descriptionFilm = userRows.getString("description");
            LocalDate releaseDateFilm = userRows.getDate("release_date").toLocalDate();
            int durationFilm = userRows.getInt("duration");
            int mpaId = userRows.getInt("id_mpa");
            Mpa mpa = new Mpa();
            mpa.setId(mpaId);
            log.info("Найден фильм: {} {}", idFilm, nameFilm);
            return Film.builder()
                    .id(idFilm)
                    .name(nameFilm)
                    .description(descriptionFilm)
                    .releaseDate(releaseDateFilm)
                    .duration(durationFilm)
                    .mpa(mpa)
                    .build();
        } else {
            log.info("Жанр с идентификатором {} не найден.", filmId);
            return null;
        }
    }

    @Override
    public boolean contains(int filmId) {
        String sql = "select * from films where id = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, filmId);

        if (userRows.next()) {
            int idFilm = userRows.getInt("id");
            String nameFilm = userRows.getString("name");
            log.info("Найден фильм: {} {}", idFilm, nameFilm);
            return true;
        } else {
            log.info("Фильм с идентификатором {} не найден.", filmId);
            return false;
        }
    }

    @Override
    public void addFilmLike(int filmId, int userId) {

    }

    @Override
    public void removeFilmLike(int filmId, int userId) {

    }

    @Override
    public List<Film> getFilmsSortedByLikes() {
        return null;
    }
}
