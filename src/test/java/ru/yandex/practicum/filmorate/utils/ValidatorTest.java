package ru.yandex.practicum.filmorate.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void validCorrectDateFilm() {
        LocalDate localDate = LocalDate.of(2000, 12, 28);
        assertTrue(Validator.validDateFilm(localDate));
    }

    @Test
    void validIncorrectDateFilm() {
        LocalDate localDate = LocalDate.of(1894, 12, 28);
        assertFalse(Validator.validDateFilm(localDate));
    }

    @Test
    void validDateFilm() {
        LocalDate localDate = LocalDate.of(1895, 12, 28);
        assertFalse(Validator.validDateFilm(localDate));
    }

    @Test
    void validCorrectFeatureDate() {
        LocalDate localDate = LocalDate.of(2000, 12, 28);
        assertTrue(Validator.validFeatureDate(localDate));
    }

    @Test
    void validIncorrectFeatureDate() {
        LocalDate localDate = LocalDate.of(2030, 12, 28);
        assertFalse(Validator.validFeatureDate(localDate));
    }

}