package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmStorage {
    Film add(Film film);
    Film remove(Film film);
    Film update(Film film);
    List<Film> getAll();
    Film get(Integer idFilm);
    boolean contains(Film film);
}
