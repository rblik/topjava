package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class Meal extends BaseEntity {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private Integer userId;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories, null);
    }

    public Meal(LocalDateTime dateTime, String description, int calories, Integer userId) {
        this(null, dateTime, description, calories, userId);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, Integer calories) {
        this(id, dateTime, description, calories, null);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories, Integer userId) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
