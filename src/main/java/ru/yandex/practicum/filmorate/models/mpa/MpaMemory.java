package ru.yandex.practicum.filmorate.models.mpa;

public enum MpaMemory {
    //UNKNOWN(0, "Неизвестный"),
    G(1, "G"),
    PG(2, "PG"),
    PG13(3, "PG-13"),
    R(4, "R"),
    NC17(5, "NC-17");

    final public int index;
    final public String name;

    MpaMemory(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
