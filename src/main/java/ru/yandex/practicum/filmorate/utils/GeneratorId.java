package ru.yandex.practicum.filmorate.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
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