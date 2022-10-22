package com.fittoo.utills;

import java.time.LocalDate;
import java.util.Calendar;

public class CalendarUtil {

    public static int getDayOfOneDay() {
        Calendar calendar = setCalendar();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getLastDayOfMonth() {
        Calendar calendar = setCalendar();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private static Calendar setCalendar() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue() - 1;
        int year = now.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar;
    }
}
