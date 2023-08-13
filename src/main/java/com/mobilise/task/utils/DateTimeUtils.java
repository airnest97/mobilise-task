package com.mobilise.task.utils;

import java.util.Date;

public class DateTimeUtils {

    public static Date fromMillis(long dateInMills) {
        Date date = new Date();
        date.setTime(dateInMills);
        return date;
    }

    public static Object fromMillis(String value) {
        try {
            long inMillis = Long.parseLong(value);
            return fromMillis(inMillis);
        } catch (Exception ignored) {
        }
        return value;
    }

    public static long getTimeMillis() {
        return new Date().getTime() * 1000;
    }

    public static String getDayOfMonthSuffix(int i) {
        if (i >= 11 && i <= 13) {
            return "th";
        }
        return switch (i % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}
