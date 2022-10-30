package ru.yandex.practicum.filmorate.utils;

public class GeneratorId {
    public static final int START_GENERATOR = 1;
    private Integer id = 1;

    public int getId() {
        return id++;
    }

    public void setStartPosition(int startPosition) {
        id = startPosition;
    }
}