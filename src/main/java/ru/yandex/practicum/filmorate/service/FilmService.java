package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private static final LocalDate DATE_EARLY = LocalDate.of(1895, 12, 28);
    private final FilmStorage filmStorage;
    private final GeneratorId generatorId;
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage, GeneratorId generatorId) {
        this.filmStorage = filmStorage;
        this.generatorId = generatorId;
        this.userStorage = userStorage;
    }

    public Film addFilm(Film film) {
        validateReleaseDateFilm(film.getReleaseDate());
        Film creatingFilm = film.toBuilder().id(generatorId.getId()).build();
        return filmStorage.add(creatingFilm);
    }

    public boolean contains(int film) {
        return filmStorage.contains(film);
    }

    public void updateFilm(Film film) {
        filmStorage.update(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public Film getFilm(int filmId) {
        if (!filmStorage.contains(filmId)) {
            return null;
        }
        return filmStorage.get(filmId);
    }

    public void addFilmLike(int id, int userId) {
        if (!userStorage.contains(userId)) {
            throw new ContainsException("Пользователя с Id " + userId + " не существует");
        }

        filmStorage.addLikeFilm(id, userId);
    }

    public void removeFilmLike(int id, int userId) {
        if (!userStorage.contains(userId)) {
            throw new ContainsException("Пользователя с Id " + userId + " не существует");
        }

        filmStorage.removeLikeFilm(id, userId);
    }

    public List<Film> getTopFilms(int count) {
        return filmStorage.getFilmsWithLikes().stream().limit(count).collect(Collectors.toList());
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
