package ru.yandex.practicum.filmorate.storage.mpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.filmorate.mapping.MpaMapping.map;

@Repository
@Qualifier("inDb")
public class DbMpaStorage implements MpaStorage {
    private final Logger log = LoggerFactory.getLogger(DbMpaStorage.class);
    private final JdbcTemplate jdbcTemplate;

    public DbMpaStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAll() {
        String sql = "select * from mpa;";
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet(sql);
        List<Mpa> mpa = new ArrayList<>();
        while (mpaRows.next()) {
            logInfoFindMpa(mpaRows);
            mpa.add(map(mpaRows));
        }
        return mpa;
    }

    @Override
    public Mpa get(int mpaId) {
        String sql = "SELECT * " +
                "FROM mpa " +
                "WHERE id = ?;";
        SqlRowSet mpaRow = jdbcTemplate.queryForRowSet(sql, mpaId);

        if (mpaRow.next()) {
            logInfoFindMpa(mpaRow);
            return map(mpaRow);
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
        SqlRowSet mpaRow = jdbcTemplate.queryForRowSet(sql, mpaId);

        if (mpaRow.next()) {
            logInfoFindMpa(mpaRow);
            return true;
        } else {
            log.info("Рейтинг MPA с идентификатором {} не найден.", mpaId);
            return false;
        }
    }

    private void logInfoFindMpa(SqlRowSet mpaRow) {
        int idInDb = mpaRow.getInt("id");
        String nameInDb = mpaRow.getString("name");
        log.info("Найден рейтинг MPA: {} {}", idInDb, nameInDb);
    }
}
