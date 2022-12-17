package ru.yandex.practicum.filmorate.storage.genre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotSupportException;
import ru.yandex.practicum.filmorate.models.genre.Genre;
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

    @Test
    void addGenre() {
        final Genre newGenre = Genre.builder().id(888).name("Not").build();

        final NotSupportException exception = assertThrows(
                NotSupportException.class,
                () -> genreStorage.add(newGenre));

        assertEquals("Данный метод не поддерживается в жанрах", exception.getMessage());
    }

    @Test
    void removeGenre() {
        final Genre removeGenre = Genre.builder().id(888).name("Not").build();

        final NotSupportException exception = assertThrows(
                NotSupportException.class,
                () -> genreStorage.remove(removeGenre));

        assertEquals("Данный метод не поддерживается в жанрах", exception.getMessage());
    }

    @Test
    void updateGenre() {
        final Genre updateGenre = Genre.builder().id(1).name("Not").build();

        final NotSupportException exception = assertThrows(
                NotSupportException.class,
                () -> genreStorage.update(updateGenre));

        assertEquals("Данный метод не поддерживается в жанрах", exception.getMessage());
    }
}