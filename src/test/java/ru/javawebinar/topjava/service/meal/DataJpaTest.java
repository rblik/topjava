package ru.javawebinar.topjava.service.meal;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles({Profiles.POSTGRES, Profiles.DATAJPA})
public class DataJpaTest extends AbstractMealServiceTest {

    public void testGetWithUser() {
        service.save(ADMIN_MEAL2, ADMIN_ID);
        Meal withUser = service.getWithUser(ADMIN_MEAL_ID + 1, ADMIN_ID);
        MealTestData.MATCHER.assertEquals(ADMIN_MEAL2, withUser);
        UserTestData.MATCHER.assertEquals(ADMIN, withUser.getUser());
    }
}
