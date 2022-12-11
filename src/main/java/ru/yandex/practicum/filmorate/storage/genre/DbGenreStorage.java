package ru.yandex.practicum.filmorate.storage.genre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.genre.Genre;

import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("inDb")
public class DbGenreStorage implements GenreStorage {
    private final Logger log = LoggerFactory.getLogger(DbGenreStorage.class);
    private final JdbcTemplate jdbcTemplate;

    public DbGenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Modifying
    public Genre add(Genre genre) {
        String sql = "INSERT INTO genre " +
                "(id, name) " +
                "VALUES (?, ?);";

        jdbcTemplate.update(sql, genre.getId(), genre.getName());

        return genre;
    }

    @Override
    @Modifying
    public Genre remove(Genre genre) {
        String sql = "DELETE FROM genre " +
                "WHERE id = ?;";

        jdbcTemplate.update(sql, genre.getId());

        return genre;
    }

    @Override
    @Modifying
    public Genre update(Genre genre) {
        String sql = "UPDATE genre " +
                "SET name = ? " +
                "WHERE id = ?;";

        jdbcTemplate.update(sql, genre.getName(), genre.getId());
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT * FROM genre;";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql);
        List<Genre> genres = new ArrayList<>();
        while (userRows.next()) {
            int idInDb = userRows.getInt("id");
            String nameInDb = userRows.getString("name");
            log.info("Найден жанр: {} {}", idInDb, nameInDb);
            genres.add(Genre.builder()
                    .id(idInDb)
                    .name(nameInDb)
                    .build());
        }
        return genres;
    }

    @Override
    public Genre get(int genreId) {
        String sql = "SELECT * " +
                "FROM genre " +
                "WHERE id = ?;";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, genreId);

        if (userRows.next()) {
            int idInDb = userRows.getInt("id");
            String nameInDb = userRows.getString("name");
            log.info("Найден жанр: {} {}", idInDb, nameInDb);
            return Genre.builder()
                    .id(idInDb)
                    .name(nameInDb)
                    .build();
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
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, genreId);

        if (userRows.next()) {
            int idInDb = userRows.getInt("id");
            String nameInDb = userRows.getString("name");
            log.info("Найден жанр: {} {}", idInDb, nameInDb);
            return true;
        } else {
            log.info("Жанр с идентификатором {} не найден.", genreId);
            return false;
        }
    }
}
