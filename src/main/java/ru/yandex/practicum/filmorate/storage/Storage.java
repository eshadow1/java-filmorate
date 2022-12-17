package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface Storage<T> {
    List<T> getAll();

    T get(int objectId);

    T add(T object);

    T remove(T object);

    T update(T object);

    boolean contains(int objectId);
}
