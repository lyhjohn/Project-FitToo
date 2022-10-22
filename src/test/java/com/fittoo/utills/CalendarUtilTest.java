package com.fittoo.utills;

import com.fittoo.member.model.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@Transactional
class CalendarUtilTest {

    @Test
    void calendarTest() {
        //given
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 9, 21); // month = 0부터
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println("dayOfWeek = " + dayOfWeek); // 요일
        System.out.println(cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // 말일
        //when
        System.out.println(LocalDate.now().getMonth());
        System.out.println(LocalDate.now().getMonthValue());
        //then
    }

    @Test
    void getCalendarTest() {
        //given
        int dayOfOneDay = CalendarUtil.getDayOfOneDay();
        int lastDayOfMonth = CalendarUtil.getLastDayOfMonth();
        System.out.println("dayOfOneDay = " + dayOfOneDay);
        System.out.println("lastDayOfMonth = " + lastDayOfMonth);
    }

}