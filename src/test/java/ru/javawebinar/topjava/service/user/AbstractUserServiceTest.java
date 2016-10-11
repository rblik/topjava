package ru.javawebinar.topjava.service.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.ServiceTest;
import ru.javawebinar.topjava.service.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.UserTestData.*;

public abstract class AbstractUserServiceTest extends ServiceTest {

    @Autowired
    protected UserService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Override
    public void testSave() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, Collections.singleton(Role.ROLE_USER));
        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, newUser, USER), service.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailSave() throws Exception {
        service.save(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Override
    public void testDelete() throws Exception {
        service.delete(USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN), service.getAll());
    }

    @Override
    public void testDeleteNotFound() throws Exception {
        service.delete(1);
    }

    @Override
    public void testGet() throws Exception {
        User user = service.get(USER_ID);
        MATCHER.assertEquals(USER, user);
    }

    @Override
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("user@yandex.ru");
        MATCHER.assertEquals(USER, user);
    }

    @Override
    public void testGetAll() throws Exception {
        Collection<User> all = service.getAll();
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, USER), all);
    }

    @Override
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(USER_ID));
    }

    @Test
    public void testGetUserWithMeal() throws Exception {
        User user = new User(null, "New", "new@gmail.com", "newPass", 1555, false, Collections.singleton(Role.ROLE_USER));
        Meal meal = new Meal(LocalDateTime.now(), "Hvchik", 500);
        meal.setUser(user);
        Collection<Meal> meals = Collections.singletonList(meal);
        user.setMeals(meals);
        service.save(user);
        User userWithMeals = service.getUserWithMeals(100010);
        MATCHER.assertEquals(user, userWithMeals);
        MealTestData.MATCHER.assertCollectionEquals(meals, userWithMeals.getMeals());
    }
}