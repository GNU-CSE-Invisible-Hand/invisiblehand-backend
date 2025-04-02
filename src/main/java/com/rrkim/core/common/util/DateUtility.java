package com.rrkim.core.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtility {

    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }

    public static String getCurrentDate(String format) {
        return convertDateTimeFormat(getCurrentDate(), format);
    }

    public static String convertDateTimeFormat(LocalDateTime localDateTime, String format) {
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString);
    }

    public static LocalDate convertStringToLocalDate(String dateTimeString) {
        return LocalDate.parse(dateTimeString);
    }

    public static long getEpochTimeFromDate(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime getDateFromEpochTime(long epochTime) {
        Instant instant = Instant.ofEpochMilli(epochTime);

        ZoneId zoneId = ZoneId.systemDefault(); // Use the system default time zone
        return instant.atZone(zoneId).toLocalDateTime();
    }
}
