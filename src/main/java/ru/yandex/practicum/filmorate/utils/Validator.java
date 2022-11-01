package ru.yandex.practicum.filmorate.utils;

import java.time.LocalDate;

public class Validator {
    private static final LocalDate DATE_EARLY = LocalDate.of(1895, 12, 28);

    private Validator() {
    }

    public static boolean isValidDateFilm(LocalDate localDate) {
        return DATE_EARLY.isBefore(localDate);
    }
}
