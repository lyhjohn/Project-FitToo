package com.fittoo.utills;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.ui.Model;

public class CalendarUtil {


	/**
	 * 모든 달의 시작 요일, 최대 일 수 구하기 위한 메서드들
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

	/**
	 * 캘린더에서 이전달, 다음달로 넘김에 따라 캘린더 view를 바꿔주기 위한 메서드
	 *
	 * @param prevMonth
	 * @param nextMonth
	 * @param year      1월 이전 or 12월 이후로 넘기면 년도를 바꿔줘야함
	 * @param model
	 * @return
	 */
	public static int[] pageControl(Integer prevMonth, Integer nextMonth, Integer year,
		Model model) {

		Map<Integer, String> dayMap = new HashMap<>();
		int[] totalDayCountAndNowMonthYear = new int[3];

		if (prevMonth != null) {
			int[] curMonthAndYear = getNewMonthAndYear(year, prevMonth);
			dayMap = getPrevMonthMap(year, prevMonth);
			model.addAttribute("year", curMonthAndYear[0]);
			model.addAttribute("currentMonth", curMonthAndYear[1]);
			totalDayCountAndNowMonthYear[0] = dayMap.size(); // 총 일 수
			totalDayCountAndNowMonthYear[1] = curMonthAndYear[1]; // 현재 월
			totalDayCountAndNowMonthYear[2] = curMonthAndYear[0]; // 현재 년도
		}

		if (nextMonth != null) {
			dayMap = getNextMonthMap(year, nextMonth);
			int[] curMonthAndYear = getNewMonthAndYear(year, nextMonth);
			model.addAttribute("year", curMonthAndYear[0]);
			model.addAttribute("currentMonth", curMonthAndYear[1]);
			totalDayCountAndNowMonthYear[0] = dayMap.size(); // 총 일 수
			totalDayCountAndNowMonthYear[1] = curMonthAndYear[1]; // 현재 월
			totalDayCountAndNowMonthYear[2] = curMonthAndYear[0]; // 현재 년도
		}

		if (prevMonth == null && nextMonth == null) {
			dayMap = getMonthMap(LocalDate.now().getYear(),
				LocalDate.now().getMonthValue());
			model.addAttribute("currentMonth", LocalDate.now().getMonthValue());
			model.addAttribute("year", LocalDate.now().getYear());
			totalDayCountAndNowMonthYear[0] = dayMap.size(); // 총 일 수
			totalDayCountAndNowMonthYear[1] = LocalDate.now().getMonthValue(); // 현재 월
			totalDayCountAndNowMonthYear[2] = LocalDate.now().getYear(); // 현재 년도
		}

		model.addAttribute("dayMap", dayMap);
		return totalDayCountAndNowMonthYear;
	}

	public static class StringOrIntegerToLocalDate {

		public static LocalDate parseDate(String date)
			throws ParseException {

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = df.parse(date);
			Calendar dateCalendar = Calendar.getInstance();

			dateCalendar.setTime(dt);

			return LocalDate.ofInstant(dateCalendar.toInstant(),
				ZoneId.systemDefault());
		}

		public static LocalDate parseDate(int year, int month, int day)
			throws ParseException {

			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month - 1, day);
			return LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
		}

		public static LocalDate getStartDate(String startDate) throws ParseException {
			return parseDate(startDate);
		}

		public static LocalDate getEndDate(String endDate) throws ParseException {
			return parseDate(endDate);
		}
	}

	public static class StringToLocalTime {

		public static LocalTime parseTime(String time)
			throws ParseException {

			DateFormat df = new SimpleDateFormat("HH:mm");
			Date dt = df.parse(time);
			Calendar timeCalendar = Calendar.getInstance();

			timeCalendar.setTime(dt);

			return LocalTime.ofInstant(timeCalendar.toInstant(),
				ZoneId.systemDefault());
		}

		public static LocalTime getStartTime(String startTime) throws ParseException {
			return parseTime(startTime);
		}

		public static LocalTime getEndTime(String endTime) throws ParseException {
			return parseTime(endTime);
		}
	}
}
