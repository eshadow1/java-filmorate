package ru.yandex.practicum.filmorate.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmServerTest {
    private FilmServer filmServer;
    private Film correctFilm;
    private Film incorrectFilm;
    private Film updateFilm;

    @BeforeEach
    public void beforeEach() {
        filmServer = new FilmServer();
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
    }

    @Test
    void addFilm() {
        filmServer.addFilm(correctFilm);
        assertEquals(1, filmServer.getAllFilms().size());
    }

    @Test
    void addIncorrectFilm() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmServer.addFilm(incorrectFilm));

        assertEquals("Некорректная дата выхода фильма", exception.getMessage());
    }

    @Test
    void containsCorrect() {
        var film = filmServer.addFilm(correctFilm);
        assertTrue(filmServer.contains(film));
    }

    @Test
    void containsIncorrect() {
        filmServer.addFilm(correctFilm);
        assertFalse(filmServer.contains(correctFilm));
    }

    @Test
    void updateFilm() {
        filmServer.addFilm(correctFilm);
        filmServer.updateFilm(updateFilm);
        assertEquals("adipisicingUpdate", filmServer.getAllFilms().get(0).getDescription());
    }

    @Test
    void getAllFilms() {
        filmServer.addFilm(correctFilm);
        assertEquals(1, filmServer.getAllFilms().size());
    }
}