package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JPA})
public class JpaTest extends UserServiceTest {
}
