package ru.javawebinar.topjava.service.meal;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles({Profiles.POSTGRES, Profiles.DATAJPA})
public class DataJpaTest extends MealServiceTest {
}
