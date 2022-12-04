package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.genre.Genre;

import java.util.List;

@Repository
@Qualifier("inDb")
public class DbGenreStorage implements GenreStorage {
    @Override
    public Genre add(Genre object) {
        return null;
    }

    @Override
    public Genre remove(Genre object) {
        return null;
    }

    @Override
    public Genre update(Genre object) {
        return null;
    }

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public Genre get(int objectId) {
        return null;
    }

    @Override
    public boolean contains(int objectId) {
        return false;
    }
}
