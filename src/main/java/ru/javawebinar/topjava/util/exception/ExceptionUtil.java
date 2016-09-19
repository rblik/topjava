package ru.javawebinar.topjava.util.exception;


/**
 * User: gkislin
 * Date: 14.05.2014
 */
public class ExceptionUtil {

    public static <T> T checkNotFoundWithUserId(T object, Integer id, int userId) {
        checkNotFound(object != null, "userId=" + userId);
        return object;
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) throw new NotFoundException("Not found entity with " + msg);
    }
}
