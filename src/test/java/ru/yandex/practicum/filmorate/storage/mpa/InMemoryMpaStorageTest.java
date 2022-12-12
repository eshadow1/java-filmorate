package ru.yandex.practicum.filmorate.storage.mpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.mpa.MpaMemory;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMpaStorageTest {
    private InMemoryMpaStorage mpaStorage;
    @BeforeEach
    public void beforeEach() {
        mpaStorage = new InMemoryMpaStorage();

    }
    @Test
    void getAll() {
        var allMpa = mpaStorage.getAll();
        assertEquals(MpaMemory.values().length, allMpa.size());
        assertEquals(MpaMemory.values()[0].index, allMpa.get(0).getId());
        assertEquals(MpaMemory.values()[0].name, allMpa.get(0).getName());
    }

    @Test
    void getMpa() {
        var mpa = mpaStorage.get(1);

        assertEquals(MpaMemory.values()[0].index, mpa.getId());
        assertEquals(MpaMemory.values()[0].name, mpa.getName());
    }

    @Test
    void getNotMpa() {
        assertNull(mpaStorage.get(-1));
    }
}