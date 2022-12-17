package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.exception.NotSupportException;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;
import ru.yandex.practicum.filmorate.storage.Storage;

public interface MpaStorage extends Storage<Mpa> {
    default Mpa add(Mpa mpa) {
        throw new NotSupportException("Данный метод не поддерживается в MPA");
    }

    default Mpa remove(Mpa mpa) {
        throw new NotSupportException("Данный метод не поддерживается в MPA");
    }

    default Mpa update(Mpa mpa) {
        throw new NotSupportException("Данный метод не поддерживается в MPA");
    }
}