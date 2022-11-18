package ru.yandex.practicum.filmorate.models;

public class Users {
    public static User getRemoveUser(Integer id) {
        return User.builder()
                .id(id)
                .email("removeUser")
                .login("removeUser")
                .name("removeUser").build();
    }
}