package com.fittoo.utills;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalendarUtil {


	/**
	 * 이 메서드 하나로 모든 달의 시작 요일, 최대 일 수 구할 수 있음
	 */
	public static Map<Integer, String> getMonthMap(int year, int currentMonth) {
		Map<Integer, String> dayMap = new HashMap<>();
		int startDay = getDayOfOneDay(year, currentMonth);
		int lastDayOfMonth = getLastDayOfMonth(year, currentMonth);
		for (int i = 1; i <= lastDayOfMonth; i++) {
			dayMap.put(i, getDay(startDay++));
			if (startDay == 8) {
				startDay = 1;
			}
		}
		return dayMap;
	}

	public static Map<Integer, String> getPrevMonthMap(int year, int prevMonth) {
		Map<Integer, String> dayMap = new HashMap<>();
		int startDay = getDayOfOneDay(year, prevMonth);
		int lastDayOfMonth = getLastDayOfMonth(year, prevMonth);
		for (int i = 1; i <= lastDayOfMonth; i++) {
			dayMap.put(i, getDay(startDay++));
			if (startDay == 8) {
				startDay = 1;
			}
		}
		return dayMap;
	}

	public static Map<Integer, String> getNextMonthMap(int year, int nextMonth) {
		Map<Integer, String> dayMap = new HashMap<>();
		int startDay = getDayOfOneDay(year, nextMonth);
		int lastDayOfMonth = getLastDayOfMonth(year, nextMonth);
		for (int i = 1; i <= lastDayOfMonth; i++) {
			dayMap.put(i, getDay(startDay++));
			if (startDay == 8) {
				startDay = 1;
			}
		}
		return dayMap;
	}

	private static int getLastDayOfMonth(int year, int month) {
		Calendar calendar = getCalendar(year, month);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	private static int getDayOfOneDay(int year, int month) {
		Calendar calendar = getCalendar(year, month);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	private static Calendar getCalendar(int year, int currentMonth) {
		LocalDate now = LocalDate.now();
		Calendar calendar = Calendar.getInstance();

		int month = currentMonth - 1;
		calendar.set(year, month, 1);
		return calendar;
	}

	private static String getDay(int day) {
		switch (day) {
			case 1:
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


	public static int[] getNewMonthAndYear(int year, int month) {
		if (month == 13) {
			int curYear = year + 1;
			int curMonth = 1;
			return new int[]{curYear, curMonth};
		}
		if (month == 0) {
			int curYear = year - 1;
			int curMonth = 12;
			return new int[]{curYear, curMonth};
		}

		return new int[]{year, month};
	}


}
