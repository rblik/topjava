package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.util.validation.ValidDate;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */

@NamedQueries({
        @NamedQuery(name = Meal.GET, query = "select m from Meal m where m.id=:id and m.user.id=:userId"),
        @NamedQuery(name = Meal.GET_ALL, query = "select m from Meal m where m.user.id=:userId order by m.dateTime desc"),
        @NamedQuery(name = Meal.DELETE, query = "delete from Meal m where m.id=:id and m.user.id=:userId"),
        @NamedQuery(name = Meal.GET_BETWEEN, query = "select m from Meal m where m.user.id=:userId and m.dateTime between :startDate and :endDate order by m.dateTime desc"),
        @NamedQuery(name = Meal.UPDATE, query = "update Meal m set m.dateTime=:dateTime, m.description=:description, m.calories=:calories where m.id=:id and m.user.id=:userId")
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time", "user_id"}, name = "meals_unique_user_datetime_idx")})
public class Meal extends BaseEntity {

    public static final String GET = "get";
    public static final String GET_ALL = "getAll";
    public static final String GET_BETWEEN = "getBetween";
    public static final String DELETE = "delete";
    public static final String UPDATE = "update";

    @Column(name = "date_time", nullable = false)
    @DateTimeFormat
    @ValidDate(message = "{ValidDate.dateTime}")
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "{NotBlank.description}")
    @Size(min = 3, message = "{Size.description}")
    private String description;

    @Column(name = "calories", nullable = false)
    @Digits(fraction = 0, integer = 4, message = "{Digits.calories}")
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
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

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
