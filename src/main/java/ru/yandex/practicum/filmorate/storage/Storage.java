package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface Storage<T> {
    T add(T object);

    T remove(T object);

    T update(T object);

    List<T> getAll();

    T get(Integer objectId);

    boolean contains(int objectId);
}
