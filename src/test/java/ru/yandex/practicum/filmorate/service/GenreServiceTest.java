package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.models.genre.GenreMemory;
import ru.yandex.practicum.filmorate.storage.genre.InMemoryGenreStorage;

import static org.junit.jupiter.api.Assertions.*;

class GenreServiceTest {
    private GenreService genreService;

    @BeforeEach
    public void beforeEach() {
        genreService = new GenreService(new InMemoryGenreStorage());
    }

    @Test
    void getAllGenres() {
        var allGenre = genreService.getAllGenres();
        assertEquals(GenreMemory.values().length, allGenre.size());
        assertEquals(GenreMemory.values()[0].index, allGenre.get(0).getId());
        assertEquals(GenreMemory.values()[0].name, allGenre.get(0).getName());
    }

    @Test
    void getGenre() {
        var genre = genreService.getGenre(1);
        assertEquals(GenreMemory.values()[0].index, genre.getId());
        assertEquals(GenreMemory.values()[0].name, genre.getName());
    }

    @Test
    void getNotGenre() {
        assertThrows(
                ContainsException.class,
                () -> genreService.getGenre(-1));
    }
}