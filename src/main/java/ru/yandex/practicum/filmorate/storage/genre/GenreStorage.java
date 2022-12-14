package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.exception.NotSupportException;
import ru.yandex.practicum.filmorate.models.genre.Genre;
import ru.yandex.practicum.filmorate.storage.Storage;

public interface GenreStorage extends Storage<Genre> {
    default Genre add(Genre genre) {
        throw new NotSupportException("Данный метод не поддерживается в жанрах");
    }

    default Genre remove(Genre genre) {
        throw new NotSupportException("Данный метод не поддерживается в жанрах");
    }

    default Genre update(Genre genre) {
        throw new NotSupportException("Данный метод не поддерживается в жанрах");
    }
}
