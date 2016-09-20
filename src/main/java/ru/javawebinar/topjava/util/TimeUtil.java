package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetween(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TME_FORMATTER);
    }

    public static LocalDate dateNow() {
        return LocalDate.now();
    }

    public static LocalDate checkDate(String localDateStr, LocalDate borderDate) {
        return (localDateStr == null || localDateStr.isEmpty()) ? borderDate : LocalDate.parse(localDateStr);
    }

    public static LocalTime checkTime(String localTimeStr, LocalTime borderTime) {
        return (localTimeStr == null || localTimeStr.isEmpty()) ? borderTime : LocalTime.parse(localTimeStr);
    }
}
