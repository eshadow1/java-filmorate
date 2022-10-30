package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class User {
    int id;
    @NonNull
    @Email
    String email;
    @NotBlank
    String login;
    String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate birthday;
}
