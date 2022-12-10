package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.GenreController;
import ru.yandex.practicum.filmorate.controllers.MpaController;
import ru.yandex.practicum.filmorate.controllers.UserController;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {FilmController.class, UserController.class, GenreController.class, MpaController.class})
public class CustomErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleContainsException(final ContainsException error) {
        return Map.of("error", HttpStatus.NOT_FOUND + ": " + error.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException error) {
        return Map.of("error", HttpStatus.BAD_REQUEST + ": " + error.getMessage());
    }
}
