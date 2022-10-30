package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static String jsonIncorrectUserDate;
    private static String jsonCorrectUserDate;
    private static String jsonUpdateUser;
    private static String endpoint;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll() {
        endpoint = "/users";
        jsonIncorrectUserDate = "{" +
                "  \"login\": \"dolore\"," +
                "  \"name\": \"Nick Name\"," +
                "  \"email\": \"mail@mail.ru\"," +
                "  \"birthday\": \"2050-08-20\"" +
                "}";
        jsonCorrectUserDate = "{" +
                "  \"login\": \"dolore\"," +
                "  \"name\": \"Nick Name\"," +
                "  \"email\": \"mail@mail.ru\"," +
                "  \"birthday\": \"1946-08-20\"" +
                "}";
        jsonUpdateUser = "{" +
                "  \"id\": 1," +
                "  \"login\": \"dolore\"," +
                "  \"name\": \"Nick\"," +
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
    void addFailLoginUser() {
        try {
            String jsonUser = "{\n" +
                    "  \"login\": \"dolore ullamco\",\n" +
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
    void addFailEmailUser() {
        try {
            String jsonUser = "{\n" +
                    "  \"login\": \"dolore ullamco\",\n" +
                    "  \"name\": \"\",\n" +
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
    void getUsers() {
        try {
            mockMvc.perform(get(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}