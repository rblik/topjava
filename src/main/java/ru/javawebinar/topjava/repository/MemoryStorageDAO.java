package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryStorageDAO implements StorageDAO<Meal> {

    private static AtomicInteger count = new AtomicInteger(0);

    private MemoryStorageDAO() {
    }

    private static final MemoryStorageDAO INSTANCE = new MemoryStorageDAO();

    public static MemoryStorageDAO getInstance() {
        if (meals.isEmpty()) {
            INSTANCE.save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            INSTANCE.save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            INSTANCE.save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            INSTANCE.save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            INSTANCE.save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            INSTANCE.save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        }

        return INSTANCE;
    }

    private static final ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void save(Meal value) {
        int i = count.incrementAndGet();
        value.setId(i);
        meals.putIfAbsent(value.getId(), value);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public void update(Meal value) {
        meals.replace(value.getId(), value);
    }
}
