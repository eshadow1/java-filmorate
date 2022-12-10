package ru.yandex.practicum.filmorate.models.genre;

public enum GenreMemory {
    //UNKNOWN(0, "Неизвестный"),
    COMEDY(1, "Комедия"),
    DRAMA(2, "Драма"),
    CARTOON(3, "Мультфильм"),
    THRILLER(4, "Триллер"),
    DOCUMENTARY(5, "Документальный"),
    ACTION(6, "Боевик");

    final public int index;
    final public String name;

    GenreMemory(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
