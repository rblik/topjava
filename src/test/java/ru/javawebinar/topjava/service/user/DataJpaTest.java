package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.getCreated;
import static ru.javawebinar.topjava.UserTestData.USER2;
import static ru.javawebinar.topjava.UserTestData.USER2_ID;

@ActiveProfiles({Profiles.POSTGRES, Profiles.DATAJPA})
public class DataJpaTest extends AbstractUserServiceTest {
    @Override
    public void testGetWithMeal() throws Exception {
        Meal meal = getCreated();
        meal.setUser(USER2);
        Collection<Meal> meals = Collections.singletonList(meal);
        USER2.setMeals(meals);
        service.save(USER2);
        User userWithMeals = service.getWithMeal(USER2_ID);
        UserTestData.MATCHER.assertEquals(USER2, userWithMeals);
        MealTestData.MATCHER.assertCollectionEquals(meals, userWithMeals.getMeals());
    }
}
