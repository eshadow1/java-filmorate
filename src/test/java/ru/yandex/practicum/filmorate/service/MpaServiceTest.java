package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.models.mpa.MpaMemory;
import ru.yandex.practicum.filmorate.storage.mpa.InMemoryMpaStorage;

import static org.junit.jupiter.api.Assertions.*;

class MpaServiceTest {
    private MpaService mpaService;

    @BeforeEach
    public void beforeEach() {
        mpaService = new MpaService(new InMemoryMpaStorage());
    }

    @Test
    void getAllGenres() {
        var allMpa = mpaService.getAllMpa();
        assertEquals(MpaMemory.values().length, allMpa.size());
        assertEquals(MpaMemory.values()[0].index, allMpa.get(0).getId());
        assertEquals(MpaMemory.values()[0].name, allMpa.get(0).getName());
    }

    @Test
    void getGenre() {
        var mpa = mpaService.getMpa(1);
        assertEquals(MpaMemory.values()[0].index, mpa.getId());
        assertEquals(MpaMemory.values()[0].name, mpa.getName());
    }

    @Test
    void getNotGenre() {
        assertThrows(
                ContainsException.class,
                () -> mpaService.getMpa(-1));
    }
}