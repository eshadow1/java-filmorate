package ru.yandex.practicum.filmorate.utils;

public class GeneratorId {
    public static final int START_GENERATOR = 1;
    private Integer id = START_GENERATOR;

    public int getId() {
        return id++;
    }

    public void setStartPosition(int startPosition) {
        id = startPosition;
    }
}