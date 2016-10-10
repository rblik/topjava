package ru.javawebinar.topjava.service.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDateTime;
import java.util.Collections;

import static ru.javawebinar.topjava.UserTestData.MATCHER;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JDBC})
public class JdbcTest extends AbstractUserServiceTest {

    @Autowired
    MealService mealService;

    @Override
    @Test
    public void testGetUserWithMeal() throws Exception {
        User user = new User(null, "New", "new@gmail.com", "newPass", 1555, false, Collections.singleton(Role.ROLE_USER));
        Meal meal = new Meal(LocalDateTime.now(), "Hvchik", 500);
        meal.setUser(user);
        service.save(user);
        mealService.save(meal, 100010);
        User userWithMeals = service.getUserWithMeals(100010);
        MATCHER.assertEquals(user, userWithMeals);
        MealTestData.MATCHER.assertCollectionEquals(Collections.singletonList(meal), userWithMeals.getMeals());
    }
}
