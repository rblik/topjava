package ru.javawebinar.topjava.web.json;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomFormatters {

    public static class DateFormatter implements Formatter<LocalDate>{
        private String pattern = "yyyy-MM-dd";
        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
        }
        @Override
        public String print(LocalDate object, Locale locale) {
            return object.format(DateTimeFormatter.ofPattern(pattern));
        }
    }
    public static class TimeFormatter implements Formatter<LocalTime>{
        private String pattern = "HH:mm:ss";
        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
            return LocalTime.parse(text, DateTimeFormatter.ofPattern(pattern));
        }
        @Override
        public String print(LocalTime object, Locale locale) {
            return object.format(DateTimeFormatter.ofPattern(pattern));
        }
    }
}
