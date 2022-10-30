package ru.yandex.practicum.filmorate.utils;

import java.time.LocalDate;

public class Validator {
    private static final LocalDate DATE_EARLY = LocalDate.of(1895, 12, 28);

    private Validator() {
    }

    public static boolean validDateFilm(LocalDate localDate) {
        return DATE_EARLY.isBefore(localDate);
    }

    public static boolean validFeatureDate(LocalDate localDate) {
        return LocalDate.now().isAfter(localDate);
    }
}
