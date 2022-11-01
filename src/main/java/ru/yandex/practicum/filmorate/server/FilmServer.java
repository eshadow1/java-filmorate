package ru.yandex.practicum.filmorate.server;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.utils.GeneratorId;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.time.LocalDate;
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
        validateReleaseDateFilm(film.getReleaseDate());

        Film creatingFilm = film.toBuilder().id(generatorId.getId()).build();
        films.put(creatingFilm.getId(), creatingFilm);
        return creatingFilm;
    }

    public boolean contains(Film film) {
        return films.containsKey(film.getId());
    }

    public void updateFilm(Film film) {
        films.put(film.getId(), film);
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    private void validateReleaseDateFilm(LocalDate releaseDateFilm) {
        if (releaseDateFilm == null) {
            throw new ValidationException("Некорректная дата выхода фильма");
        }
        if (!Validator.isValidDateFilm(releaseDateFilm)) {
            throw new ValidationException("Некорректная дата выхода фильма");
        }
    }
}
