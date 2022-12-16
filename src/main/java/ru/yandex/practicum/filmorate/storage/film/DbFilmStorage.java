package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapping.FilmMapping;
import ru.yandex.practicum.filmorate.models.film.Film;
import ru.yandex.practicum.filmorate.models.genre.Genre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());

        String sqlFilm = "SELECT films.*, mpa.name AS mpa_name " +
                "FROM films " +
                "JOIN mpa ON films.id_mpa = mpa.id " +
                "WHERE films.name = ? AND " +
                "films.description = ? AND " +
                "films.release_date = ? AND " +
                "films.duration = ? AND " +
                "films.id_mpa = ?;";

        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sqlFilm, film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());


        if (!filmRows.next()) {
            return null;
        }

        int idFilm = filmRows.getInt("id");
        addGenres(idFilm, film.getGenres());

        return mapFilm(filmRows);
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

        List<Genre> genresCurrentFilm = new ArrayList<>(genres);
        this.jdbcTemplate.batchUpdate(
                sqlGenre,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, id);
                        ps.setInt(2, genresCurrentFilm.get(i).getId());
                    }

                    public int getBatchSize() {
                        return genresCurrentFilm.size();
                    }
                });
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT films.*, mpa.name AS mpa_name " +
                "FROM films " +
                "JOIN mpa ON films.ID_MPA = mpa.id;";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql);
        List<Film> films = new ArrayList<>();

        while (filmRows.next()) {
            logInfoFindFilm(filmRows);
            films.add(mapFilm(filmRows));
        }

        return films;
    }

    @Override
    public Film get(int filmId) {
        String sql = "SELECT films.*, mpa.name AS mpa_name " +
                "FROM films " +
                "JOIN mpa ON films.ID_MPA = mpa.id " +
                "WHERE films.id = ?;";
        SqlRowSet filmsRows = jdbcTemplate.queryForRowSet(sql, filmId);

        if (filmsRows.next()) {
            logInfoFindFilm(filmsRows);
            return mapFilm(filmsRows);
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
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql, filmId);

        if (filmRows.next()) {
            logInfoFindFilm(filmRows);
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
        String sql = "SELECT films.*, mpa.name AS mpa_name " +
                "FROM films " +
                "JOIN mpa ON films.ID_MPA = mpa.id " +
                "LEFT JOIN likes_film LF ON films.id = LF.id_film " +
                "GROUP BY films.id " +
                "ORDER BY COUNT(LF.id_film) DESC;";

        SqlRowSet filmsRows = jdbcTemplate.queryForRowSet(sql);
        List<Film> films = new ArrayList<>();

        while (filmsRows.next()) {
            logInfoFindFilm(filmsRows);
            films.add(mapFilm(filmsRows));
        }
        return films;
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

    private void logInfoFindFilm(SqlRowSet filmRows) {
        int idFilm = filmRows.getInt("ID");
        String nameFilm = filmRows.getString("NAME");
        log.info("Найден фильм: {} {}", idFilm, nameFilm);
    }

    private Film mapFilm(SqlRowSet filmRow) {
        int idFilm = filmRow.getInt("ID");
        Set<Genre> genre = getGenres(idFilm);

        return FilmMapping.map(filmRow, genre);
    }
}
