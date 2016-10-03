package ru.javawebinar.topjava.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface ValidDate {
    String message() default "Date is not correct";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
