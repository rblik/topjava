package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    List<Meal> findByUserIdOrderByDateTimeDesc(Integer id);

    Meal findOneByIdAndUserId(Integer id, Integer userId);

    List<Meal> findByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);

//    could work if we would call the @NamedQuery User.Delete
//    Long Delete(@Param("id") Integer id, @Param("userId") Integer userId);

    @Modifying
    @Transactional
//    @Query("DELETE FROM Meal m WHERE m.id=?1 AND m.user.id=?2")
    @Query(name = Meal.DELETE)
    Integer deleteMeal(@Param("id") Integer id, @Param("userId") Integer userId);

    @Query("select m from Meal m join fetch m.user where m.id=?1 and m.user.id=?2")
    Meal getWithUser(int id, int userId);
}
