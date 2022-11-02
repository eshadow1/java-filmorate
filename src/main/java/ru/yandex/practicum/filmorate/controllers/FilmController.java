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

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmServer filmServer;

    public FilmController() {
        this.filmServer = new FilmServer();
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма: " + film);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(filmServer.addFilm(film));
        } catch (ValidationException error) {
            log.warn(error.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(film);
        }
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма " + film.getId() + ": " + film);

        if (!filmServer.contains(film)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(film);
        }

        filmServer.updateFilm(film);
        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос на получение всех фильмов");
        return filmServer.getAllFilms();
    }
}

