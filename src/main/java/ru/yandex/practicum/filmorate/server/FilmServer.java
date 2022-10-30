package ru.yandex.practicum.filmorate.server;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.utils.GeneratorId;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmServer {
    private final Map<Integer, Film> films;
    private final GeneratorId generatorId;

    public FilmServer() {
        this.films = new HashMap<>();
        this.generatorId = new GeneratorId();
    }

    public Film addFilm(Film film) {
        if (!Validator.validDateFilm(film.getReleaseDate())) {
            throw new ValidationException("Некорректная дата выхода фильма");
        }
        Film creatingFilm = film.toBuilder().id(generatorId.getId()).build();
        films.put(creatingFilm.getId(), creatingFilm);
        return creatingFilm;
    }

    public boolean contains(Film film) {
        if (!Validator.validDateFilm(film.getReleaseDate())) {
            throw new ValidationException("Некорректная дата выхода фильма");
        }
        return films.containsKey(film.getId());
    }

    public Film updateFilm(Film film) {
        return films.replace(film.getId(), film);
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }
}
