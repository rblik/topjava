package ru.javawebinar.topjava.service.meal;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JPA})
public class JpaTest extends AbstractMealServiceTest {
}
