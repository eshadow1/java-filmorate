package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.models.film.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

public interface FilmStorage extends FilmLikesStorage, Storage<Film> {
}
