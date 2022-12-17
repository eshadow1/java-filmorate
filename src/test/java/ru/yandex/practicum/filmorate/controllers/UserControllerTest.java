package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserControllerTest {
    private static String jsonCorrectUserDate;
    private static String endpoint;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll() {
        endpoint = "/users";

        jsonCorrectUserDate = "{" +
                "  \"login\": \"dolore\"," +
                "  \"name\": \"Nick Name\"," +
                "  \"email\": \"mail@mail.ru\"," +
                "  \"birthday\": \"1946-08-20\"" +
                "}";
    }

    @Test
    void addCorrectUser() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addFailBirthdayUser() {
        try {
            String jsonUser = "{\n" +
                    "  \"login\": \"doloreullamco\",\n" +
                    "  \"name\": \"Name\",\n" +
                    "  \"email\": \"yandex@mail.ru\",\n" +
                    "  \"birthday\": \"2446-08-20\"\n" +
                    "}";
            mockMvc.perform(post(endpoint)
                    .content(jsonUser)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addFailLoginWithSpaceUser() {
        try {
            String jsonUser = "{\n" +
                    "  \"login\": \"dolore ullamco\",\n" +
                    "  \"name\": \"Name\",\n" +
                    "  \"email\": \"yandex@mail.ru\",\n" +
                    "  \"birthday\": \"1980-08-20\"\n" +
                    "}";
            mockMvc.perform(post(endpoint)
                    .content(jsonUser)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addFailLoginEmptyUser() {
        try {
            String jsonUser = "{\n" +
                    "  \"login\": \"\",\n" +
                    "  \"name\": \"Name\",\n" +
                    "  \"email\": \"yandex@mail.ru\",\n" +
                    "  \"birthday\": \"1980-08-20\"\n" +
                    "}";
            mockMvc.perform(post(endpoint)
                    .content(jsonUser)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addFailEmailUser() {
        try {
            String jsonUser = "{\n" +
                    "  \"login\": \"doloreullamco\",\n" +
                    "  \"name\": \"Name\",\n" +
                    "  \"email\": \"mail.ru\",\n" +
                    "  \"birthday\": \"1980-08-20\"\n" +
                    "}";
            mockMvc.perform(post(endpoint)
                    .content(jsonUser)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addIncorrectUser() {
        try {
            String jsonIncorrectUserDate = "{" +
                    "  \"login\": \"dolore\"," +
                    "  \"names\": \"Nick Name\"," +
                    "  \"test\": \"mail@mail.ru\"," +
                    "  \"date\": \"2050-08-20\"" +
                    "}";
            mockMvc.perform(post(endpoint)
                    .content(jsonIncorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateUser() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            String jsonUpdateUser = "{" +
                    "  \"id\": 1," +
                    "  \"login\": \"dolore\"," +
                    "  \"name\": \"Nick\"," +
                    "  \"email\": \"mail@mail.ru\"," +
                    "  \"birthday\": \"1946-08-20\"" +
                    "}";
            mockMvc.perform(put(endpoint)
                            .content(jsonUpdateUser)
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Nick"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateNotFoundUser() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            String jsonUpdateUser = "{" +
                    "  \"id\": 10," +
                    "  \"login\": \"dolore\"," +
                    "  \"name\": \"Nick\"," +
                    "  \"email\": \"mail@mail.ru\"," +
                    "  \"birthday\": \"1946-08-20\"" +
                    "}";
            mockMvc.perform(put(endpoint)
                            .content(jsonUpdateUser)
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUsers() {
        try {
            mockMvc.perform(get(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUnknownUser() {
        try {
            mockMvc.perform(get(endpoint + "/-1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUser() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            mockMvc.perform(get(endpoint + "/1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getFriendsUser() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            mockMvc.perform(get(endpoint + "/1/friends")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getFriendsUnknownUser() {
        try {
            mockMvc.perform(get(endpoint + "/-1/friends")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addFriendUnknownUser() {
        try {
            mockMvc.perform(put(endpoint + "/-1/friends/2")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void removeFriendUnknownUser() {
        try {
            mockMvc.perform(delete(endpoint + "/-1/friends/2")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addFriendUser() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            mockMvc.perform(put(endpoint + "/1/friends/2")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void removeFriendUser() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            mockMvc.perform(delete(endpoint + "/1/friends/2")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getFriendsUserWithOtherUser() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectUserDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            mockMvc.perform(get(endpoint + "/1/friends/common/2")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}