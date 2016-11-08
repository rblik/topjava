package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.TestUtil.userAuth;
import static ru.javawebinar.topjava.UserTestData.USER;

public class AjaxMealControllerTest extends AbstractControllerTest{

    private static final String AJAX_URL = "/ajax/profile/meals/";

    @Test
    public void testNotValid() throws Exception {
        String response = mockMvc.perform(post(AJAX_URL).with(userAuth(USER))
                .param("dateTime", "2016-11-07 19:20")
                .param("description", "Food")
                .param("calories", ""))
                .andExpect(status().isUnprocessableEntity()).andReturn().getResponse().getContentAsString();
        assertThat(response, containsString("calories may not be null"));
    }

    @Test
    public void testValid() throws Exception {
        mockMvc.perform(post(AJAX_URL).with(userAuth(USER))
                .param("dateTime", "2016-11-07 19:20")
                .param("description", "Food")
                .param("calories", "500"))
                .andExpect(status().isOk());
    }
}
