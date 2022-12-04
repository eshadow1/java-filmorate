package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;

import java.util.List;

@Repository
@Qualifier("inDb")
public class DbMpaStorage implements MpaStorage {
    @Override
    public Mpa add(Mpa object) {
        return null;
    }

    @Override
    public Mpa remove(Mpa object) {
        return null;
    }

    @Override
    public Mpa update(Mpa object) {
        return null;
    }

    @Override
    public List<Mpa> getAll() {
        return null;
    }

    @Override
    public Mpa get(int objectId) {
        return null;
    }

    @Override
    public boolean contains(int objectId) {
        return false;
    }
}
