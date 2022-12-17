package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;
import ru.yandex.practicum.filmorate.models.mpa.MpaMemory;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Qualifier("inMemory")
public class InMemoryMpaStorage implements MpaStorage {
    private final Map<Integer, Mpa> mpas;

    public InMemoryMpaStorage() {
        this.mpas = new HashMap<>(
                Arrays.stream(MpaMemory.values()).collect(
                        Collectors.toMap(mpa -> mpa.index, mpa ->
                                Mpa.builder()
                                        .id(mpa.index)
                                        .name(mpa.name)
                                        .build())
                ));
    }

    @Override
    public List<Mpa> getAll() {
        return new ArrayList<>(mpas.values());
    }

    @Override
    public Mpa get(int mpaId) {
        return mpas.getOrDefault(mpaId, null);
    }

    @Override
    public boolean contains(int mpaId) {
        return mpas.containsKey(mpaId);
    }
}
