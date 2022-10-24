package com.fittoo.utills;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalendarUtil {


	/**
	 * 이 메서드 하나로 모든 달의 시작 요일, 최대 일 수 구할 수 있음
	 */
	public static Map<Integer, String> getDayMap(int currentMonth) {
		Map<Integer, String> dayMap = new HashMap<>();
		int startDay = getDayOfOneDay(currentMonth);
		int lastDayOfMonth = getLastDayOfMonth(currentMonth);
		for (int i = 1; i <= lastDayOfMonth; i++) {
			dayMap.put(i, getDay(startDay++));
			if (startDay == 8) {
				startDay = 1;
			}
		}
		return dayMap;
	}

	private static int getDayOfOneDay(int currentMonth) {

		Calendar calendar = getCalendar(currentMonth);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}


	private static int getLastDayOfMonth(int currentMonth) {
		Calendar calendar = getCalendar(currentMonth);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	private static Calendar getCalendar(int currentMonth) {
		LocalDate now = LocalDate.now();
		Calendar calendar = Calendar.getInstance();
		if (currentMonth == 0) {
			int month = now.getMonthValue() - 1;
			int year = now.getYear();
			calendar.set(year, month, 1);
			return calendar;
		} else {
			int month = currentMonth + 1;
			int year = now.getYear();
			if (month == 13) {
				month = 1;
				year++;
			}
			calendar.set(year, month, 1);
			return calendar;
		}
	}

	private static String getDay(int day) {
		switch (day) {
			case 1 :
				return "일";
			case 2:
				return "월";
			case 3:
				return "화";
			case 4:
				return "수";
			case 5:
				return "목";
			case 6:
				return "금";
			case 7:
				return "토";
		}
		return "";
	}
}
