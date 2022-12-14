package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.film.Film;
import ru.yandex.practicum.filmorate.models.genre.Genre;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Qualifier("inDb")
public class DbFilmStorage implements FilmStorage {
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
                "VALUES (?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql, film.getName(),
                film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId());

        String sqlFilm = "SELECT * " +
                "FROM films " +
                "WHERE name = ? AND " +
                "description = ? AND " +
                "release_date = ? AND " +
                "duration = ? AND " +
                "id_mpa = ?;";

        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sqlFilm, film.getName(),
                film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId());


        if (!userRows.next()) {
            return null;
        }

        int idFilm = userRows.getInt("id");
        String nameFilm = userRows.getString("name");
        String descriptionFilm = userRows.getString("description");
        LocalDate releaseDateFilm = userRows.getDate("release_date").toLocalDate();
        int durationFilm = userRows.getInt("duration");
        int mpaId = userRows.getInt("id_mpa");

        addGenres(idFilm, film.getGenres());

        Mpa mpa = getMpa(mpaId);
        Set<Genre> genre = getGenres(idFilm);

        log.info("Найден фильм: {} {}", idFilm, nameFilm);
        return Film.builder()
                .id(idFilm)
                .name(nameFilm)
                .description(descriptionFilm)
                .releaseDate(releaseDateFilm)
                .duration(durationFilm)
                .mpa(mpa)
                .genres(genre)
                .build();
    }

    @Override
    @Modifying
    public Film remove(Film film) {
        String sql = "DELETE FROM films " +
                "WHERE id = ?;";
        jdbcTemplate.update(sql, film.getId());
        return film;
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
                "WHERE id = ?;";

        jdbcTemplate.update(sql, film.getName(),
                film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());

        addGenres(film.getId(), film.getGenres());
        return get(film.getId());
    }

    private void addGenres(int id, Set<Genre> genres) {
        String sqlOldGenre = "DELETE FROM genres_film " +
                "WHERE id_film = ?;";
        jdbcTemplate.update(sqlOldGenre, id);

        if (genres == null || genres.isEmpty()) {
            return;
        }

        String sqlGenre = "INSERT INTO genres_film " +
                "(id_film, id_genre) " +
                "VALUES (?, ?);";

        for (Genre genre : genres) {
            jdbcTemplate.update(sqlGenre, id, genre.getId());
        }
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT * FROM films;";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql);
        List<Film> films = new ArrayList<>();

        while (filmRows.next()) {
            var film = mapFilm(filmRows);
            log.info("Найден фильм: {} {}", film.getId(), film.getName());
            films.add(film);
        }

        return films;
    }

    @Override
    public Film get(int filmId) {
        String sql = "SELECT * " +
                "FROM films " +
                "WHERE id = ?;";
        SqlRowSet filmsRows = jdbcTemplate.queryForRowSet(sql, filmId);

        if (filmsRows.next()) {
            var film = mapFilm(filmsRows);
            log.info("Найден фильм: {} {}", film.getId(), film.getName());
            return film;
        } else {
            log.info("Фильм с идентификатором {} не найден.", filmId);
            return null;
        }
    }

    @Override
    public boolean contains(int filmId) {
        String sql = "SELECT * " +
                "FROM films " +
                "WHERE id = ?;";
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
    @Modifying
    public void addFilmLike(int filmId, int userId) {
        String sql = "INSERT INTO likes_film " +
                "(id_film, id_user) " +
                "VALUES (?, ?);";

        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    @Modifying
    public void removeFilmLike(int filmId, int userId) {
        String sql = "DELETE FROM likes_film " +
                "WHERE id_film = ? AND " +
                "id_user = ?;";

        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> getFilmsSortedByLikes() {
        String sql = "SELECT * " +
                "FROM films " +
                "LEFT JOIN GENRES_FILM GF ON FILMS.ID = GF.ID_FILM " +
                "GROUP BY films.id " +
                "ORDER BY COUNT(GF.id_film) DESC;";

        SqlRowSet filmsRows = jdbcTemplate.queryForRowSet(sql);
        List<Film> films = new ArrayList<>();

        while (filmsRows.next()) {
            var film = mapFilm(filmsRows);
            log.info("Найден фильм: {} {}", film.getId(), film.getName());
            films.add(film);
        }
        return films;
    }

    private Mpa getMpa(int mpaId) {
        String sqlMpa = "SELECT * " +
                "FROM mpa " +
                "WHERE id = ?;";
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet(sqlMpa, mpaId);
        Mpa mpa = new Mpa();
        mpa.setId(mpaId);
        if (mpaRows.next()) {
            mpa.setName(mpaRows.getString("name"));
        }
        return mpa;
    }

    private Set<Genre> getGenres(int idFilm) {
        String sqlGenre = "SELECT * " +
                "FROM genre " +
                "WHERE id IN (SELECT id_genre " +
                "FROM genres_film " +
                "WHERE id_film = ?);";
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet(sqlGenre, idFilm);
        Set<Genre> genre = new HashSet<>();
        while (genreRows.next()) {
            genre.add(Genre.builder()
                    .id(genreRows.getInt("id"))
                    .name(genreRows.getString("name"))
                    .build());
        }
        return genre;
    }

    private Film mapFilm(SqlRowSet userRows) {
        int idFilm = userRows.getInt("id");
        String nameFilm = userRows.getString("name");
        String descriptionFilm = userRows.getString("description");
        LocalDate releaseDateFilm = userRows.getDate("release_date").toLocalDate();
        int durationFilm = userRows.getInt("duration");
        int mpaId = userRows.getInt("id_mpa");
        Mpa mpa = getMpa(mpaId);
        Set<Genre> genre = getGenres(idFilm);


        return Film.builder()
                .id(idFilm)
                .name(nameFilm)
                .description(descriptionFilm)
                .releaseDate(releaseDateFilm)
                .duration(durationFilm)
                .mpa(mpa)
                .genres(genre)
                .build();
    }
}
