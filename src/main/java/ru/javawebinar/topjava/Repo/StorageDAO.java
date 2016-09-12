package ru.javawebinar.topjava.Repo;

import java.util.List;

public interface StorageDAO<T> {

    List<T> getAll();

    void add(T value);

    void delete(int id);

    T get(int id);

    void update(T value);

}
