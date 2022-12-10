package ru.yandex.practicum.filmorate.models.genre;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Genre {
    int id;
    String name;
}
