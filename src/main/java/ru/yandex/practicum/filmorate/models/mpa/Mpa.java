package ru.yandex.practicum.filmorate.models.mpa;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Mpa {
    @NonNull
    int id;

    @NotBlank
    String name;
}


