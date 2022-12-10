package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.models.film.Film;
import ru.yandex.practicum.filmorate.models.genre.Genre;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Qualifier("inMemory")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films;
    private final Map<Integer, Set<Integer>> filmLikes;
    private final GeneratorId generatorId;

    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    public InMemoryFilmStorage(@Qualifier("inMemory") MpaStorage mpaStorage,
                               @Qualifier("inMemory") GenreStorage genreStorage,
                               GeneratorId generatorId) {
        this.films = new HashMap<>();
        this.filmLikes = new HashMap<>();
        this.generatorId = generatorId;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public Film add(Film film) {
        Mpa currentMpa = getMpaParameter(film.getMpa());
        List<Genre> currentGenres = getGenreParameters(film.getGenres());

        Film creatingFilm = film.toBuilder()
                .id(generatorId.getId())
                .genres(currentGenres)
                .mpa(currentMpa)
                .build();
        films.put(creatingFilm.getId(), creatingFilm);
        filmLikes.put(creatingFilm.getId(), new HashSet<>());
        return creatingFilm;
    }

    @Override
    public Film remove(Film film) {
        filmLikes.remove(film.getId());
        return films.remove(film.getId());
    }

    @Override
    public Film update(Film film) {
        Mpa currentMpa = getMpaParameter(film.getMpa());
        List<Genre> currentGenres = getGenreParameters(film.getGenres());

        Film creatingFilm = film.toBuilder()
                .genres(currentGenres)
                .mpa(currentMpa)
                .build();
        films.put(film.getId(), creatingFilm);
        return creatingFilm;
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

    private List<Genre> getGenreParameters(List<Genre> genres) {
        if(genres == null || genres.isEmpty()) {
            return new ArrayList<>();
        }

        return genres.stream()
                .distinct()
                .map(genre -> genreStorage.get(genre.getId()))
                .collect(Collectors.toList());
    }

    private Mpa getMpaParameter(Mpa mpa) {
        if(mpa == null) {
            throw new ContainsException("У фильма нет рейтинга MPA");
        }

        return mpaStorage.get(mpa.getId());
    }
}
