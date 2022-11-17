package ru.yandex.practicum.filmorate.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    @Test
    void getRemoveUser() {
        var newUser = Users.getRemoveUser(1);
        assertEquals("removeUser", newUser.getName());
        assertEquals("removeUser", newUser.getLogin());
        assertEquals("removeUser", newUser.getEmail());
    }
}