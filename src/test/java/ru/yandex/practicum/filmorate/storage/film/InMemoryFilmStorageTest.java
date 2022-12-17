package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.film.Film;
import ru.yandex.practicum.filmorate.models.genre.Genre;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;
import ru.yandex.practicum.filmorate.storage.genre.InMemoryGenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.InMemoryMpaStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryFilmStorageTest {
    private InMemoryFilmStorage filmStorage;
    private Film correctFilm;
    private Film correctFilmWithGenre;
    private Film updateFilm;
    private int idFilm;
    private Mpa mpa;

    @BeforeEach
    public void beforeEach() {
        filmStorage = new InMemoryFilmStorage(new InMemoryMpaStorage(),
                new InMemoryGenreStorage(), new GeneratorId());
        LocalDate localDate = LocalDate.of(1967, 3, 25);
        idFilm = 1;
        mpa = Mpa.builder().id(1).build();
        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.builder().id(1).build());
        correctFilm = Film.builder()
                .id(idFilm)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(localDate)
                .duration(100)
                .mpa(mpa)
                .build();
        correctFilmWithGenre = Film.builder()
                .id(idFilm)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(localDate)
                .duration(100)
                .mpa(mpa)
                .genres(genres)
                .build();
        updateFilm = Film.builder()
                .id(idFilm)
                .name("nisi eiusmod")
                .description("adipisicingUpdate")
                .releaseDate(localDate)
                .duration(100)
                .mpa(mpa)
                .build();
    }

    @Test
    void add() {
        filmStorage.add(correctFilm);
        assertTrue(filmStorage.contains(correctFilm.getId()));
        assertEquals(correctFilm.getId(), filmStorage.get(correctFilm.getId()).getId());
        assertEquals(correctFilm.getName(), filmStorage.get(correctFilm.getId()).getName());
        assertEquals(correctFilm.getDuration(), filmStorage.get(correctFilm.getId()).getDuration());
        assertEquals(correctFilm.getDescription(), filmStorage.get(correctFilm.getId()).getDescription());
    }

    @Test
    void addWithGenre() {
        filmStorage.add(correctFilmWithGenre);
        assertTrue(filmStorage.contains(correctFilmWithGenre.getId()));
        assertEquals(correctFilm.getId(), filmStorage.get(correctFilmWithGenre.getId()).getId());
        assertEquals(correctFilm.getName(), filmStorage.get(correctFilmWithGenre.getId()).getName());
        assertEquals(correctFilm.getDuration(), filmStorage.get(correctFilmWithGenre.getId()).getDuration());
        assertEquals(correctFilm.getDescription(), filmStorage.get(correctFilmWithGenre.getId()).getDescription());
    }

    @Test
    void remove() {
        filmStorage.add(correctFilm);
        filmStorage.remove(correctFilm);
        assertFalse(filmStorage.contains(correctFilm.getId()));
    }

    @Test
    void update() {
        filmStorage.add(correctFilm);
        filmStorage.update(updateFilm);
        assertEquals("adipisicingUpdate", filmStorage.get(correctFilm.getId()).getDescription());

    }

    @Test
    void getAll() {
        filmStorage.add(correctFilm);
        assertEquals(idFilm, filmStorage.getAll().size());
    }

    @Test
    void addLikeFilm() {
        filmStorage.add(correctFilm);
        LocalDate localDate = LocalDate.of(1968, 3, 25);
        int idFilm2 = 2;
        var correctFilm2 = Film.builder()
                .id(idFilm2)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(localDate)
                .duration(180)
                .mpa(mpa)
                .build();
        filmStorage.add(correctFilm2);

        int idUser = 1;
        filmStorage.addFilmLike(idFilm2, idUser);
        assertEquals(idFilm2, filmStorage.getFilmsSortedByLikes().get(0).getId());
    }

    @Test
    void checkCorrectSortedFilm() {
        filmStorage.add(correctFilm);
        LocalDate localDate = LocalDate.of(1968, 3, 25);
        int idFilm2 = 2;
        var correctFilm2 = Film.builder()
                .id(idFilm2)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(localDate)
                .duration(180)
                .mpa(mpa)
                .build();

        int idFilm3 = 3;
        var correctFilm3 = Film.builder()
                .id(idFilm3)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(localDate)
                .duration(180)
                .mpa(mpa)
                .build();

        filmStorage.add(correctFilm2);
        filmStorage.add(correctFilm3);

        int idUser = 1;
        int idUser2 = 2;
        filmStorage.addFilmLike(idFilm2, idUser);
        filmStorage.addFilmLike(idFilm3, idUser);
        filmStorage.addFilmLike(idFilm3, idUser2);

        assertEquals(idFilm3, filmStorage.getFilmsSortedByLikes().get(0).getId());
        assertEquals(idFilm2, filmStorage.getFilmsSortedByLikes().get(1).getId());
    }

    @Test
    void removeLikeFilm() {
        filmStorage.add(correctFilm);
        LocalDate localDate = LocalDate.of(1968, 3, 25);
        int idFilm2 = 2;
        var correctFilm2 = Film.builder()
                .id(idFilm2)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(localDate)
                .duration(180)
                .mpa(mpa)
                .build();
        filmStorage.add(correctFilm2);

        int idUser = 2;
        filmStorage.addFilmLike(idFilm2, idUser);
        filmStorage.removeFilmLike(idFilm2, idUser);
        assertEquals(idFilm, filmStorage.getFilmsSortedByLikes().get(0).getId());
    }
}