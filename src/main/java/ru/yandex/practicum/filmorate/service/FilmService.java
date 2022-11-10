package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.time.LocalDate;
import java.util.List;

@Service
public class FilmService {

    private static final LocalDate DATE_EARLY = LocalDate.of(1895, 12, 28);

    private final FilmStorage filmStorage;
    private final GeneratorId generatorId;

    public FilmService(FilmStorage filmStorage, GeneratorId generatorId) {
        this.filmStorage = filmStorage;
        this.generatorId = generatorId;
    }

    public Film addFilm(Film film) {
        validateReleaseDateFilm(film.getReleaseDate());
        Film creatingFilm = film.toBuilder().id(generatorId.getId()).build();
        return filmStorage.add(creatingFilm);
    }

    public boolean contains(Film film) {
        return filmStorage.contains(film);
    }

    public void updateFilm(Film film) {
        filmStorage.update(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    private static boolean isValidDateFilm(LocalDate localDate) {
        return DATE_EARLY.isBefore(localDate);
    }

    private void validateReleaseDateFilm(LocalDate releaseDateFilm) {
        if (releaseDateFilm == null || !isValidDateFilm(releaseDateFilm)) {
            throw new ValidationException("Некорректная дата выхода фильма");
        }
    }
}
