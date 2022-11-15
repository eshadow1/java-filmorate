package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmLikesStorage {
    void addFilmLike(int filmId, int userId);
    void removeFilmLike(int filmId, int userId);
    List<Film> getFilmsWithLikes();
}
