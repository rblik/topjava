package ru.javawebinar.topjava.service.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDateTime;
import java.util.Collections;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JDBC})
public class JdbcTest extends AbstractUserServiceTest {

    @Autowired
    MealService mealService;

    @Override
    @Test
    public void testGetUserWithMeal() throws Exception {
        Meal meal = new Meal(LocalDateTime.now(), "Havchik", 500);
        meal.setUser(USER2);
        service.save(USER2);
        mealService.save(meal, USER2_ID);
        User userWithMeals = service.getUserWithMeals(USER2_ID);
        MATCHER.assertEquals(USER2, userWithMeals);
        MealTestData.MATCHER.assertCollectionEquals(Collections.singletonList(meal), userWithMeals.getMeals());
    }
}
