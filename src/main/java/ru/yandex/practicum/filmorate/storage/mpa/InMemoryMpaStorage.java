package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.mpa.Mpa;
import ru.yandex.practicum.filmorate.models.mpa.MpaMemory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Qualifier("inMemory")
public class InMemoryMpaStorage implements MpaStorage {
    private final Map<Integer, Mpa> mpas;

    public InMemoryMpaStorage() {
        this.mpas = new HashMap<>(
                Arrays.stream(MpaMemory.values()).collect(
                        Collectors.toMap(mpa -> mpa.index, mpa ->
                                new Mpa(mpa.index, mpa.name))
                ));
    }

    @Override
    public Mpa add(Mpa mpa) {
        return mpas.put(mpa.getId(), mpa);
    }

    @Override
    public Mpa remove(Mpa mpa) {
        return mpas.remove(mpa.getId());
    }

    @Override
    public Mpa update(Mpa mpa) {
        return mpas.put(mpa.getId(), mpa);
    }

    @Override
    public List<Mpa> getAll() {
        return null;
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
