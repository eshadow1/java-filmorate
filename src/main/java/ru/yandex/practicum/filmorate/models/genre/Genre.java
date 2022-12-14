package ru.yandex.practicum.filmorate.models.genre;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
public class Genre {
    @NonNull
    int id;

    String name;
}
