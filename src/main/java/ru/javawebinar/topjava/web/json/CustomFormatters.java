package ru.javawebinar.topjava.web.json;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class CustomFormatters {

    public static class DateFormatter implements Formatter<LocalDate>{
        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return TimeUtil.parseLocalDate(text);
        }
        @Override
        public String print(LocalDate object, Locale locale) {
            return object.toString();
        }
    }
    public static class TimeFormatter implements Formatter<LocalTime>{
        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
            return TimeUtil.parseLocalTime(text);
        }
        @Override
        public String print(LocalTime object, Locale locale) {
            return object.toString();
        }
    }
}
