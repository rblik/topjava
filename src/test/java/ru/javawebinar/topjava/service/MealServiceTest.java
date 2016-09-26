package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import static java.time.temporal.ChronoUnit.WEEKS;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    protected MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() {
        Meal newMeal = new Meal(LocalDateTime.now(), "Ужин", 800);
        Meal savedMeal = service.save(newMeal, USER_ID);
        newMeal.setId(savedMeal.getId());
        MATCHER.assertEquals(newMeal, savedMeal);
    }

    @Test
    public void testDelete() {
        service.delete(100008, ADMIN_ID);
        Collection<Meal> initState = service.getAll(ADMIN_ID);
        Collection<Meal> finalState = Collections.singletonList(ADMIN_MEAL);
        MATCHER.assertCollectionEquals(finalState, initState);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() {
        service.delete(ADMIN_MEAL_ID, USER_ID);
        service.delete(10, USER_ID);
    }

    @Test
    public void testGet() {
        MATCHER.assertEquals(service.get(USER_MEAL_ID, USER_ID), USER_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundGet() {
        service.get(ADMIN_MEAL_ID, USER_ID);
        service.get(10, USER_ID);
    }

    @Test
    public void testGetFiltered() {
        Assert.assertEquals(service.getBetweenDateTimes(LocalDateTime.now()
                .minus(1, WEEKS), LocalDateTime.now(), ADMIN_ID).size(), 2);
    }

    @Test
    public void testUpdate() {
        Meal meal = new Meal(USER_MEAL);
        meal.setCalories(1000);
        service.update(meal, USER_ID);
        Meal mealUpdated = service.get(USER_MEAL_ID, USER_ID);
        MATCHER.assertEquals(meal, mealUpdated);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() {
        Meal adminMeal = new Meal(ADMIN_MEAL);
        Meal userMeal = new Meal(USER_MEAL);
        userMeal.setId(10);
        adminMeal.setCalories(1500);
        service.update(adminMeal, USER_ID);
        service.update(userMeal, USER_ID);
    }
}
