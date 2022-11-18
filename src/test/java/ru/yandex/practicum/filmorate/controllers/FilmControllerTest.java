package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {
    private static String jsonCorrectFilmDate;
    private static String endpoint;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll() {
        endpoint = "/films";

        jsonCorrectFilmDate = "{" +
                "\"name\": \"nisi eiusmod\"," +
                "\"description\": \"adipisicing\"," +
                "\"releaseDate\": \"1967-03-25\"," +
                "\"duration\": 100" +
                "}";
    }

    @Test
    void addCorrectFilm() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectFilmDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addFailDateFilm() {
        try {
            String jsonFailFilmDate = "{" +
                    "  \"name\": \"Name\"," +
                    "  \"description\": \"Description\"," +
                    "  \"releaseDate\": \"1890-03-25\"," +
                    "  \"duration\": 200" +
                    "}";
            mockMvc.perform(post(endpoint)
                    .content(jsonFailFilmDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addIncorrectFilm() {
        try {
            String jsonIncorrectFilmDate = "{" +
                    "  \"test\": \"Name\"," +
                    "  \"second\": \"Description\"," +
                    "  \"release\": \"1990-03-25\"," +
                    "  \"durations\": 200" +
                    "}";
            mockMvc.perform(post(endpoint)
                    .content(jsonIncorrectFilmDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addFailNameFilm() {
        try {
            String jsonUser = "{" +
                    "\"name\": \"\"," +
                    "\"description\": \"Description\"," +
                    "\"releaseDate\": \"1900-03-25\"," +
                    "\"duration\": 200\n" +
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
    void addFailDescriptionFilm() {
        try {
            String jsonUser = "{" +
                    "\"name\": \"Film name\"," +
                    "\"description\": \"Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят " +
                    "разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов,  " +
                    "который за время «своего отсутствия», стал кандидатом Коломбани.\"," +
                    "\"releaseDate\": \"1900-03-25\"," +
                    "\"duration\": 200" +
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
    void addFailDurationFilm() {
        try {
            String jsonUser = "{\n" +
                    "  \"name\": \"Name\",\n" +
                    "  \"description\": \"Descrition\",\n" +
                    "  \"releaseDate\": \"1980-03-25\",\n" +
                    "  \"duration\": -200\n" +
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
    void updateFilm() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectFilmDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            String jsonUpdateFilm = "{" +
                    "\"id\": 1," +
                    "\"name\": \"nisi eiusmod\"," +
                    "\"description\": \"adipisicingUpdate\"," +
                    "\"releaseDate\": \"1967-03-25\"," +
                    "\"duration\": 100" +
                    "}";

            mockMvc.perform(put(endpoint)
                            .content(jsonUpdateFilm)
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andExpect(jsonPath("$.description").value("adipisicingUpdate"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateNotFoundFilm() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectFilmDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            String jsonUpdateFilm = "{" +
                    "\"id\": 2," +
                    "\"name\": \"nisi eiusmod\"," +
                    "\"description\": \"adipisicingUpdate\"," +
                    "\"releaseDate\": \"1967-03-25\"," +
                    "\"duration\": 100" +
                    "}";

            mockMvc.perform(put(endpoint)
                            .content(jsonUpdateFilm)
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getFilms() {
        try {
            mockMvc.perform(get(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUnknownFilm() {
        try {
            mockMvc.perform(get(endpoint+"/-1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getFilm() {
        try {
            mockMvc.perform(post(endpoint)
                    .content(jsonCorrectFilmDate)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

            mockMvc.perform(get(endpoint+"/1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getPopularFilms() {
        try {
            mockMvc.perform(get(endpoint+"/popular")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addLikeUnknownFilm() {
        try {
            mockMvc.perform(put(endpoint + "/-1/like/1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void removeLikeUnknownFilm() {
        try {
            mockMvc.perform(delete(endpoint + "/-1/like/1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}