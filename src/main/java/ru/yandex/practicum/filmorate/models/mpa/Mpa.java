package ru.yandex.practicum.filmorate.models.mpa;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Mpa {
    @NonNull
    int id;

    String name;
}


