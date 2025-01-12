package com.tiam.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateManager {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public static String getCurrentDate() {
        return LocalDate.now().format(formatter);
    }

    public static LocalDate parseLocalDate(String dateString) {
        return LocalDate.parse(dateString, formatter);
    }

    public static String parseString(LocalDate date) {
        return date.format(formatter);
    }
    
}
