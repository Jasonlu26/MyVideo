package com.yx.play.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * @description
 */
public class DateUtil {

    public static String getVideoTimeString(long millis) {
        long seconds = new BigDecimal(millis).divide(new BigDecimal(1000)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        Locale locale = Locale.getDefault();
        String standardTime;
        if (millis <= 0) {
            standardTime = "00:00";
        } else if (millis < 60 * 1000) {
            standardTime = String.format(locale, "00:%02d", seconds);
        } else if (millis < 60 * 60 * 1000) {
            standardTime = String.format(locale, "%02d:%02d", seconds / 60, seconds % 60);
        } else {
            long hour = seconds / (60 * 60);
            long time = seconds - hour * 60 * 60;
            standardTime = String.format(locale, "%02d:%02d:%02d", hour, time / 60, time % 60);
        }
//        StringBuilder formatBuilder = new StringBuilder();
//        Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());
//        String standardTime = Util.getStringForTime(formatBuilder, formatter, millis);
        return standardTime;
    }

    public static Long getBetweenDays(String time) {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.parse(time);
        return Math.abs(ChronoUnit.DAYS.between(date2, date1));
    }
}
