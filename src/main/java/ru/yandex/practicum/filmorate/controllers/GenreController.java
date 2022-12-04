package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.models.genre.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genresService;

    public GenreController(GenreService genresService) {
        this.genresService = genresService;
    }

    @GetMapping
    public List<Genre> getGenres() {
        log.info("Получен запрос на получение всех жанров");
        return genresService.getAllGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getFilm(@PathVariable int id) {
        log.info("Получен запрос на получение жанра " + id);
        return ResponseEntity.status(HttpStatus.OK).body(genresService.getGenre(id));
    }
}
