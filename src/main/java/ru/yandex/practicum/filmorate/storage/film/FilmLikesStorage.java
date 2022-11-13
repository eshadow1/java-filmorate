package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmLikesStorage {
    void addLikeFilm(int filmId, int userId);
    void removeLikeFilm(int filmId, int userId);
    List<Film> getFilmsWithLikes();
}
