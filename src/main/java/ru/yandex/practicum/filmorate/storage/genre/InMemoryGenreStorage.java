package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.genre.Genre;
import ru.yandex.practicum.filmorate.models.genre.GenreMemory;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Qualifier("inMemory")
public class InMemoryGenreStorage implements GenreStorage {
    private final Map<Integer, Genre> genres;

    public InMemoryGenreStorage() {
        this.genres = new HashMap<>(
                Arrays.stream(GenreMemory.values()).collect(
                        Collectors.toMap(id -> id.index, id ->
                                Genre.builder()
                                        .id(id.index)
                                        .name(id.name)
                                        .build())
                ));
    }

    @Override
    public Genre add(Genre genre) {
        return genres.put(genre.getId(), genre);
    }

    @Override
    public Genre remove(Genre genre) {
        return genres.remove(genre.getId());
    }

    @Override
    public Genre update(Genre genre) {
        return genres.put(genre.getId(), genre);
    }

    @Override
    public List<Genre> getAll() {

        return new ArrayList<>(genres.values());
    }

    @Override
    public Genre get(int genreId) {
        return genres.getOrDefault(genreId, null);
    }

    @Override
    public boolean contains(int genreId) {
        return genres.containsKey(genreId);
    }
}
