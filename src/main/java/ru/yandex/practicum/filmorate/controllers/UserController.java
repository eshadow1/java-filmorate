package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.server.UserServer;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private final UserServer userServer;

    public UserController() {
        this.userServer = new UserServer();
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на добавление пользователя.");

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userServer.addUser(user));
        } catch (ValidationException error) {
            log.warn(error.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя " + user.getId() + ".");

        if (!userServer.contains(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
        }

        try {
            userServer.updateUser(user);
        } catch (ValidationException error) {
            log.warn(error.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен запрос на получение всех пользователей.");
        return userServer.getAllUsers();
    }
}
