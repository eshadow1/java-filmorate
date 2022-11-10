package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {

        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма: " + film);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(filmService.addFilm(film));
        } catch (ValidationException error) {
            log.warn(error.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(film);
        }
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма " + film.getId() + ": " + film);

        if (!filmService.contains(film)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(film);
        }

        filmService.updateFilm(film);
        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос на получение всех фильмов");
        return filmService.getAllFilms();
    }
}

