package com.confitech.test.blogapp.util;

import com.confitech.test.blogapp.exception.InvalidDateFormatException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateTimeUtil {

    private static final List<DateTimeFormatter> FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
    );

    public static LocalDateTime parseDateTime(String date) {
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDateTime.parse(date, formatter);
            } catch (DateTimeParseException e) {
                // Ignore to try the next format
            }
        }

        try {
            return LocalDateTime.parse(date + "T00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date format: " + date);
        }
    }
}