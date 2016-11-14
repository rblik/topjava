package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.to.UserTo;

import javax.servlet.http.HttpServletRequest;

/**
 * User: gkislin
 * Date: 23.09.2014
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(DataIntegrityViolationException.class)
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    ModelAndView dataIntegrityViolationHandler(HttpServletRequest req, DataIntegrityViolationException e) {
        ModelAndView mav;
        LOG.error("DataIntegrityViolationException at request " + req.getRequestURL(), e);
        if (e.getRootCause().getLocalizedMessage().contains("email")) {
            e = new DataIntegrityViolationException("User with this email already present in application");
            mav = new ModelAndView("profile");
        } else {
            mav = new ModelAndView("exception/exception");
        }
        mav.addObject("exception", e);

        AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.getUserTo());
        } else {
            mav.addObject("userTo", new UserTo());
            mav.addObject("register", true);
        }
        return mav;
    }

    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        LOG.error("Exception at request " + req.getRequestURL(), e);
        ModelAndView mav = new ModelAndView("exception/exception");
        mav.addObject("exception", e);

        // Interceptor is not invoked, put userTo
        AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.getUserTo());
        }
        return mav;
    }
}
