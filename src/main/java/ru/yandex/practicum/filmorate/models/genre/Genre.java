package ru.yandex.practicum.filmorate.models.genre;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@Builder(toBuilder = true)
public class Genre {
    @NonNull
    int id;

    @NotBlank
    String name;
}
