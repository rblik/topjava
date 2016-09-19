package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        return service.save(meal);
    }

    public void delete(int id, int userId) {
        service.delete(id, userId);
    }

    public Meal get(int id, int userId) {
        return service.get(id, userId);
    }

    public List<MealWithExceed> getAll(int userId) {
        if (userId == -1) {
            return Collections.emptyList();
        } else {
            return MealsUtil.getWithExceeded(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        }
    }

    public List<MealWithExceed> getFilteredByDate(int userId, LocalDateTime beginDateTime, LocalDateTime endDateTime) {
        if (userId == -1) {
            return Collections.emptyList();
        } else {
            System.out.println();
            List<Meal> filteredByDate = service.getFilteredByDate(userId, beginDateTime.toLocalDate(), endDateTime.toLocalDate());
            return MealsUtil.getFilteredWithExceeded(filteredByDate, beginDateTime.toLocalTime(), endDateTime.toLocalTime(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        }
    }
}
