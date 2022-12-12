package ru.yandex.practicum.filmorate.storage.genre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.genre.GenreMemory;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryGenreStorageTest {

    private InMemoryGenreStorage genreStorage;
    @BeforeEach
    public void beforeEach() {
        genreStorage = new InMemoryGenreStorage();

    }
    @Test
    void getAll() {
        var allGenre = genreStorage.getAll();
        assertEquals(GenreMemory.values().length, allGenre.size());
        assertEquals(GenreMemory.values()[0].index, allGenre.get(0).getId());
        assertEquals(GenreMemory.values()[0].name, allGenre.get(0).getName());
    }

    @Test
    void getGenre() {
        var genre = genreStorage.get(1);

        assertEquals(GenreMemory.values()[0].index, genre.getId());
        assertEquals(GenreMemory.values()[0].name, genre.getName());
    }

    @Test
    void getNotGenre() {
        assertNull(genreStorage.get(-1));
    }
}