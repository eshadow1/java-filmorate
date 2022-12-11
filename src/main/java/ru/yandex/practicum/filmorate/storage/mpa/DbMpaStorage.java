package ru.yandex.practicum.filmorate.storage.mpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;

import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("inDb")
public class DbMpaStorage implements MpaStorage {
    private final Logger log = LoggerFactory.getLogger(DbMpaStorage.class);
    private final JdbcTemplate jdbcTemplate;

    public DbMpaStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Modifying
    public Mpa add(Mpa mpa) {
        String sql = "INSERT INTO mpa " +
                "(id, name) " +
                "VALUES (?, ?);";

        jdbcTemplate.update(sql, mpa.getId(),
                mpa.getName());

        return mpa;
    }

    @Override
    @Modifying
    public Mpa remove(Mpa mpa) {
        String sql = "DELETE FROM mpa " +
                "WHERE id = ?;";

        jdbcTemplate.update(sql, mpa.getId());

        return mpa;
    }

    @Override
    @Modifying
    public Mpa update(Mpa mpa) {
        String sql = "UPDATE mpa " +
                "SET name = ? " +
                "WHERE id = ?;";

        jdbcTemplate.update(sql, mpa.getName(), mpa.getId());
        return mpa;
    }

    @Override
    public List<Mpa> getAll() {
        String sql = "select * from mpa;";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql);
        List<Mpa> mpa = new ArrayList<>();
        while (userRows.next()) {
            int idInDb = userRows.getInt("id");
            String nameInDb = userRows.getString("name");
            log.info("Найден рейтинг MPA: {} {}", idInDb, nameInDb);
            mpa.add(Mpa.builder()
                    .id(idInDb)
                    .name(nameInDb)
                    .build());
        }
        return mpa;
    }

    @Override
    public Mpa get(int mpaId) {
        String sql = "SELECT * " +
                "FROM mpa " +
                "WHERE id = ?;";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, mpaId);

        if (userRows.next()) {
            int idInDb = userRows.getInt("id");
            String nameInDb = userRows.getString("name");
            log.info("Найден рейтинг MPA: {} {}", idInDb, nameInDb);
            return Mpa.builder()
                    .id(idInDb)
                    .name(nameInDb)
                    .build();
        } else {
            log.info("Рейтинг MPA с идентификатором {} не найден.", mpaId);
            return null;
        }
    }

    @Override
    public boolean contains(int mpaId) {
        String sql = "SELECT * " +
                "FROM mpa " +
                "WHERE id = ?;";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, mpaId);

        if (userRows.next()) {
            int idInDb = userRows.getInt("id");
            String nameInDb = userRows.getString("name");
            log.info("Найден рейтинг MPA: {} {}", idInDb, nameInDb);
            return true;
        } else {
            log.info("Рейтинг MPA с идентификатором {} не найден.", mpaId);
            return false;
        }
    }
}
