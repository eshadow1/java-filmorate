package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.models.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films;
    private final Map<Integer, Set<Integer>> filmLikes;

    public InMemoryFilmStorage() {
        this.films = new HashMap<>();
        this.filmLikes = new HashMap<>();
    }

    @Override
    public Film add(Film film) {
        films.put(film.getId(), film);
        filmLikes.put(film.getId(), new HashSet<>());
        return film;
    }

    @Override
    public Film remove(Film film) {
        filmLikes.remove(film.getId());
        return films.remove(film.getId());
    }

    @Override
    public Film update(Film film) {
        return films.put(film.getId(), film);
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film get(int idFilm) {
        return films.get(idFilm);
    }

    @Override
    public boolean contains(int idFilm) {
        return films.containsKey(idFilm);
    }

    @Override
    public void addFilmLike(int filmId, int userId) {
        checkedFilmLikesContains(filmId);

        filmLikes.get(filmId).add(userId);
    }

    @Override
    public void removeFilmLike(int filmId, int userId) {
        checkedFilmLikesContains(filmId);

        filmLikes.get(filmId).remove(userId);
    }

    @Override
    public List<Film> getFilmsSortedByLikes() {
        return filmLikes.entrySet().stream()
                .sorted((a, b) -> b.getValue().size() - a.getValue().size())
                .map(a -> films.get(a.getKey())).collect(Collectors.toList());
    }

    private void checkedFilmLikesContains(int id) {
        if (!filmLikes.containsKey(id)) {
            throw new ContainsException("Фильм с Id " + id + " не найден");
        }
    }
}
