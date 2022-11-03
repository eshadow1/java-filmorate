package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class User {
    int id;

    @NonNull
    @Email
    String email;

    @NotBlank
    @Pattern(regexp = "\\S+")
    String login;

    String name;

    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate birthday;
}
