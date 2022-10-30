package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.server.FilmServer;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private final FilmServer filmServer;

    public FilmController() {
        this.filmServer = new FilmServer();
    }

    @PostMapping("/films")
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма.");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(filmServer.addFilm(film));
        } catch (ValidationException error) {
            log.warn(error.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(film);
        }
    }

    @PutMapping("/films")
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма " + film.getId() + ".");

        if (!filmServer.contains(film)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(film);
        }

        try {
            filmServer.updateFilm(film);
        } catch (ValidationException error) {
            log.warn(error.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(film);
        }

        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос на получение всех фильмов.");
        return filmServer.getAllFilms();
    }
}
