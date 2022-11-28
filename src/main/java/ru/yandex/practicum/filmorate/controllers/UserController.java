package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.models.user.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на добавление пользователя: " + user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя " + user.getId() + ": " + user);

        userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}/friends/{friendsId}")
    public ResponseEntity<List<User>> addUserFriend(@PathVariable int id, @PathVariable int friendsId) {
        log.info("Получен запрос на добавление друга с id " + friendsId + " у пользователя с id" + id);

        userService.addFriend(id, friendsId);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getFriends(id));
    }

    @DeleteMapping("/{id}/friends/{friendsId}")
    public ResponseEntity<List<User>> removeUserFriend(@PathVariable int id, @PathVariable int friendsId) {
        log.info("Получен запрос на удаление друга с id " + friendsId + " у пользователя с id" + id);
        userService.removeFriend(id, friendsId);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getFriends(id));
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getFriendsUser(@PathVariable int id) {
        log.info("Получен запрос на получение всех друзей пользователя с id " + id);

        return ResponseEntity.status(HttpStatus.OK).body(userService.getFriends(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getFriendsUserWithOtherUser(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен запрос на получение общих друзей пользователей c id " + id + " и " + otherId);

        return ResponseEntity.status(HttpStatus.OK).body(userService.getCommonFriends(id, otherId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        log.info("Получен запрос на получение пользователя " + id);

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получен запрос на получение всех пользователей");
        return userService.getAllUsers();
    }
}
