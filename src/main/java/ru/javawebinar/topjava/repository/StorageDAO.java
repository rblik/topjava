package ru.javawebinar.topjava.repository;

import java.util.List;

public interface StorageDAO<T> {

    List<T> getAll();

    void save(T value);

    void delete(int id);

    T get(int id);

    void update(T value);

}
