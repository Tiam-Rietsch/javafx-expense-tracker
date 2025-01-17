package com.tiam.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateManager {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM");
    private static final DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

    public static String getCurrentDate() {
        return LocalDate.now().format(formatter);
    }

    public static String getCurrentMonth() {
        return LocalDate.now().format(monthFormatter);
    }

    public static String getCurrentYear() {
        return LocalDate.now().format(yearFormatter);
    }

    public static LocalDate parseLocalDate(String dateString) {
        return LocalDate.parse(dateString, formatter);
    }

    public static String parseString(LocalDate date) {
        return date.format(formatter);
    }
    
}
