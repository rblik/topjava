package ru.javawebinar.topjava.Repo;

import java.util.List;

public interface StorageDAO<T> {

    List<T> values();

    void add(T value);

    void delete(int id);

    T get(int id);

    void edit(T value);

}
