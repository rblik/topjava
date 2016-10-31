package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractJpaUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractJpaUserServiceTest {
    @Test
    public void testGetWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.MEALS, user.getMeals());
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithMealsNotFound() throws Exception {
        service.getWithMeals(1);
    }

    @Test
    public void testEnableDisable() {
        assertTrue(service.get(USER_ID).isEnabled());
        service.toggleEnabled(false, USER_ID);
        assertFalse(service.get(USER_ID).isEnabled());
        service.toggleEnabled(true, USER_ID);
        assertTrue(service.get(USER_ID).isEnabled());
        service.toggleEnabled(true, USER_ID);
        assertTrue(service.get(USER_ID).isEnabled());
    }
}