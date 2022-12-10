package ru.yandex.practicum.filmorate.models.film;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.models.genre.Genre;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class Film {
    int id;

    @NotBlank
    String name;

    @Size(max = 200)
    String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate releaseDate;

    @PositiveOrZero
    int duration;

    Mpa mpa;
    List<Genre> genres;
}
