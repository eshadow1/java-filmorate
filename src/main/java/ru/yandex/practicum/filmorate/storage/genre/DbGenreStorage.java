package ru.yandex.practicum.filmorate.storage.genre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.genre.Genre;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.filmorate.mapping.GenreMapping.map;

@Repository
@Qualifier("inDb")
public class DbGenreStorage implements GenreStorage {
    private final Logger log = LoggerFactory.getLogger(DbGenreStorage.class);
    private final JdbcTemplate jdbcTemplate;

    public DbGenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT * FROM genre;";
        SqlRowSet genreRow = jdbcTemplate.queryForRowSet(sql);
        List<Genre> genres = new ArrayList<>();
        while (genreRow.next()) {
            logInfoFindGenre(genreRow);
            genres.add(map(genreRow));
        }
        return genres;
    }

    @Override
    public Genre get(int genreId) {
        String sql = "SELECT * " +
                "FROM genre " +
                "WHERE id = ?;";
        SqlRowSet genreRow = jdbcTemplate.queryForRowSet(sql, genreId);

        if (genreRow.next()) {
            logInfoFindGenre(genreRow);
            return map(genreRow);
        } else {
            log.info("Жанр с идентификатором {} не найден.", genreId);
            return null;
        }
    }

    @Override
    public boolean contains(int genreId) {
        String sql = "SELECT * " +
                "FROM genre " +
                "WHERE id = ?;";
        SqlRowSet genreRow = jdbcTemplate.queryForRowSet(sql, genreId);

        if (genreRow.next()) {
            logInfoFindGenre(genreRow);
            return true;
        } else {
            log.info("Жанр с идентификатором {} не найден.", genreId);
            return false;
        }
    }

    private void logInfoFindGenre(SqlRowSet genreRow) {
        int idInDb = genreRow.getInt("id");
        String nameInDb = genreRow.getString("name");
        log.info("Найден жанр: {} {}", idInDb, nameInDb);
    }
}
