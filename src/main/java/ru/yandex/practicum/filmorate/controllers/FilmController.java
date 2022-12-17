package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.film.Film;
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
    @JsonProperty(required = true)
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
    @JsonProperty(required = true)
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма " + film.getId() + ": " + film);

        Film updateFilm = filmService.updateFilm(film);

        return ResponseEntity.status(HttpStatus.OK).body(updateFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> addLikeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос на добавления лайка фильму с id " + id + " от пользователя " + userId);

        filmService.addFilmLike(id, userId);

        return ResponseEntity.status(HttpStatus.OK).body(filmService.getFilm(id));
    }


    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> removeLikeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос на удаление лайка у фильма с id " + id + " от пользователя " + userId);

        filmService.removeFilmLike(id, userId);

        return ResponseEntity.status(HttpStatus.OK).body(filmService.getFilm(id));
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос на получение всех фильмов");
        return filmService.getAllFilms();
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получен запрос на получение первых " + count + " популярных фильмов");
        return filmService.getTopFilms(count);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable int id) {
        log.info("Получен запрос на получение фильма " + id);
        return ResponseEntity.status(HttpStatus.OK).body(filmService.getFilm(id));
    }
}

