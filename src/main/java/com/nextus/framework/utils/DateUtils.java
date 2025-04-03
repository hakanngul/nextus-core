package com.nextus.framework.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateUtils provides utility methods for working with date and time.
 */
public final class DateUtils {

    private static final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private DateUtils() {
        // Utility class
    }

    public static String getToday() {
        return LocalDate.now().format(DEFAULT_FORMAT);
    }

    public static String getDatePlusDays(int days) {
        return LocalDate.now().plusDays(days).format(DEFAULT_FORMAT);
    }

    public static String getDateMinusDays(int days) {
        return LocalDate.now().minusDays(days).format(DEFAULT_FORMAT);
    }

    public static String format(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(TIMESTAMP_FORMAT);
    }

    public static LocalDate parseDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }
}