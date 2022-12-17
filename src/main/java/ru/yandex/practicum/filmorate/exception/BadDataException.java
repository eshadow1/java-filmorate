package ru.yandex.practicum.filmorate.exception;

public class BadDataException extends RuntimeException {
    public BadDataException(String message) {
        super(message);
    }
}