package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.Month;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(EXCEED_MATCHER.contentListMatcher(MEALS_WITH_EXCEED));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print());
        MATCHER.assertCollectionEquals(asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(USER_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        String body = mockMvc.perform(delete(REST_URL + ADMIN_MEAL_ID))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        assertEquals("Not found entity with id=" + ADMIN_MEAL_ID, JsonUtil.readValue(body, String.class));
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "between")
                .param("startDate", "2015-05-31")
                .param("endDate", "2015-06-02")
                .param("startTime", "06:00:00")
                .param("endTime", "22:00:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(EXCEED_MATCHER.contentListMatcher(HALF_MEAL_WITH_EXCEED));
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(null, of(2016, Month.OCTOBER, 24, 5, 0), "Ужин", 510);
        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(REST_MEAL)))
                .andExpect(status().isCreated());
        Meal meal = MATCHER.fromJsonAction(resultActions);
        expected.setId(meal.getId());
        MATCHER.assertEquals(meal, expected);
        MATCHER.assertCollectionEquals(asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = new Meal(100002, now().truncatedTo(SECONDS), "Ужин", 510);
        ResultActions resultActions = mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(status().isOk());
        Meal updated = MATCHER.fromJsonAction(resultActions);
        MATCHER.assertEquals(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Meal meal = new Meal(100002, now().truncatedTo(SECONDS), "Ужин", 510);
        String body = mockMvc.perform(put(REST_URL + ADMIN_MEAL_ID)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertEquals("Not found entity with id=" + ADMIN_MEAL_ID, JsonUtil.readValue(body, String.class));
    }
}