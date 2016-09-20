package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //will show two users
//            adminUserController.getAll().forEach(System.out::println);
            MealServiceImpl mealService = appCtx.getBean(MealServiceImpl.class);


//            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
//            mealRestController.save(new Meal(LocalDateTime.now(), "Borsh", 1000, 1));


//            mealRestController.getAll(1).forEach(System.out::println);
        }
    }
}
