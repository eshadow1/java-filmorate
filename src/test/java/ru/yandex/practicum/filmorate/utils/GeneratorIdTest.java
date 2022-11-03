package ru.yandex.practicum.filmorate.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorIdTest {
    GeneratorId generatorId;

    @BeforeEach
    public void beforeEach() {
        generatorId = new GeneratorId();
    }

    @Test
    void getId() {
        int newStartPosition = 1;
        assertEquals(newStartPosition, generatorId.getId());
    }

    @Test
    void setStartPosition() {
        int newStartPosition = 2;
        generatorId.setStartPosition(newStartPosition);
        assertEquals(newStartPosition, generatorId.getId());
    }

    @Test
    void setDefaultStartPosition() {
        generatorId.setStartPosition(GeneratorId.START_GENERATOR);
        assertEquals(GeneratorId.START_GENERATOR, generatorId.getId());
    }
}