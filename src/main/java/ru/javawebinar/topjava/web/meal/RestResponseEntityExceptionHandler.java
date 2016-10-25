package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static ru.javawebinar.topjava.web.json.CustomFormatters.DateFormatter;
import static ru.javawebinar.topjava.web.json.CustomFormatters.TimeFormatter;

@ControllerAdvice(assignableTypes = {MealRestController.class})
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), NOT_FOUND, request);
    }

//    The same as in spring-mvc.xml:10
    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter());
        binder.addCustomFormatter(new TimeFormatter());
    }
}
