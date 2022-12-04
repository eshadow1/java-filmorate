package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.film.Film;

import java.util.List;

@Repository
@Qualifier("inDb")
public class DbFilmStorage implements FilmStorage{
    @Override
    public Film add(Film object) {
        return null;
    }

    @Override
    public Film remove(Film object) {
        return null;
    }

    @Override
    public Film update(Film object) {
        return null;
    }

    @Override
    public List<Film> getAll() {
        return null;
    }

    @Override
    public Film get(int objectId) {
        return null;
    }

    @Override
    public boolean contains(int objectId) {
        return false;
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
