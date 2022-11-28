package ru.yandex.practicum.filmorate.models.film;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

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

    Genre genre;

    RatingMPA ratingMPA;
}
