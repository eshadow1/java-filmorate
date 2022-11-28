package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.film.Film;
import ru.yandex.practicum.filmorate.models.user.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.GeneratorId;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmServiceTest {
    private FilmService filmService;
    private UserStorage userStorage;
    private Film correctFilm;
    private Film incorrectFilm;
    private Film updateFilm;
    private Film nullDateFilm;

    @BeforeEach
    public void beforeEach() {
        userStorage = new InMemoryUserStorage();
        filmService = new FilmService(new InMemoryFilmStorage(), userStorage, new GeneratorId());
        LocalDate localDate = LocalDate.of(1967, 3, 25);
        LocalDate localDateIncorrect = LocalDate.of(1867, 3, 25);
        correctFilm = Film.builder()
                .id(0)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(localDate)
                .duration(100).build();
        updateFilm = Film.builder()
                .id(1)
                .name("nisi eiusmod")
                .description("adipisicingUpdate")
                .releaseDate(localDate)
                .duration(100).build();
        incorrectFilm = Film.builder()
                .id(0)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(localDateIncorrect)
                .duration(100).build();
        nullDateFilm = Film.builder()
                .id(0)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(null)
                .duration(100).build();
    }

    @Test
    void addFilm() {
        filmService.addFilm(correctFilm);
        assertEquals(1, filmService.getAllFilms().size());
    }

    @Test
    void addIncorrectFilm() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmService.addFilm(incorrectFilm));

        assertEquals("Некорректная дата выхода фильма", exception.getMessage());
    }

    @Test
    void addNullDateFilm() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmService.addFilm(nullDateFilm));

        assertEquals("Некорректная дата выхода фильма", exception.getMessage());
    }

    @Test
    void containsCorrect() {
        var film = filmService.addFilm(correctFilm);
        assertTrue(filmService.contains(film.getId()));
    }

    @Test
    void containsIncorrect() {
        filmService.addFilm(correctFilm);
        assertFalse(filmService.contains(correctFilm.getId()));
    }

    @Test
    void updateFilm() {
        filmService.addFilm(correctFilm);
        filmService.updateFilm(updateFilm);
        assertEquals("adipisicingUpdate", filmService.getAllFilms().get(0).getDescription());
    }

    @Test
    void getAllFilms() {
        filmService.addFilm(correctFilm);
        assertEquals(1, filmService.getAllFilms().size());
    }

    @Test
    void getFilm() {
        filmService.addFilm(correctFilm);
        assertEquals(1, filmService.getFilm(1).getId());
    }

    @Test
    void getTopFilm() {
        filmService.addFilm(correctFilm);
        assertEquals(1, filmService.getTopFilms(1).size());
    }

    @Test
    void getNoAddedFilm() {
        assertThrows(
                ContainsException.class,
                () -> filmService.getFilm(1));
    }

    @Test
    void addLikeNoAddedUser() {
        filmService.addFilm(correctFilm);
        assertThrows(
                ContainsException.class,
                () -> filmService.addFilmLike(1, 1));
    }

    @Test
    void addLikeUser() {
        LocalDate localDate = LocalDate.of(2000, 3, 25);
        userStorage.add(User.builder()
                .id(1)
                .name("Nick Name")
                .email("mail@mail.ru")
                .login("dolore")
                .birthday(localDate).build());
        filmService.addFilm(correctFilm);
        int idFilm2 = 2;
        var correctFilm2 = Film.builder()
                .id(idFilm2)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(localDate)
                .duration(180).build();
        filmService.addFilm(correctFilm2);
        filmService.addFilmLike(1, 1);
        assertEquals(1, filmService.getTopFilms(1).get(0).getId());
    }

    @Test
    void removeLikeNoAddedUser() {
        filmService.addFilm(correctFilm);
        assertThrows(
                ContainsException.class,
                () -> filmService.removeFilmLike(1, 1));
    }
}