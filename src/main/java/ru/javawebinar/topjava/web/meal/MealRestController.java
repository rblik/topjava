package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
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

    private HttpSession session;

    private int userId;

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public Meal save(Meal meal) {
        userId = this.getUserId();
        meal.setUserId(userId);
        return service.save(meal, userId);
    }

    public void delete(int id) {
        userId = this.getUserId();
        service.delete(id, userId);
    }

    public Meal get(int id) {
        userId = this.getUserId();
        return service.get(id, userId);
    }

    public List<MealWithExceed> getAll() {
        userId = this.getUserId();
        return (userId == -1) ? Collections.emptyList() :
                MealsUtil.getWithExceeded(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealWithExceed> getFilteredByDateAndTime(String beginDateStr, String endDateStr,
                                                         String beginTimeStr, String endTimeStr) {
        userId = this.getUserId();

        LocalDate beginDate = TimeUtil.checkDate(beginDateStr, LocalDate.MIN);
        LocalDate endDate = TimeUtil.checkDate(endDateStr, LocalDate.MAX);
        LocalTime beginTime = TimeUtil.checkTime(beginTimeStr, LocalTime.MIN);
        LocalTime endTime = TimeUtil.checkTime(endTimeStr, LocalTime.MAX);

        if (beginDate.isAfter(endDate) || beginTime.isAfter(endTime) || userId == -1) {
            return Collections.emptyList();
        } else {
            return MealsUtil.getFilteredWithExceeded(service.getFilteredByDate(userId, beginDate, endDate),
                    beginTime, endTime, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        }
    }

    public int getUserId() {
        Object sessionUser = session.getAttribute("sessionUser");
        return (sessionUser == null) ? -1 : ((User) sessionUser).getId();
    }
}
