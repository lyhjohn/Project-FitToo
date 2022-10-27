package com.fittoo.reservation.util;

import com.fittoo.trainer.model.ScheduleDto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.thymeleaf.util.ListUtils;

public class UnschedulableDateMark {

	public static Map<Integer, Boolean> canReserveDate(int[] totalDayCountAndNowMonthYear, String trainerId,
		Optional<List<ScheduleDto>> optionalList) {
		int totalDayCount = totalDayCountAndNowMonthYear[0];
		Map<Integer, Boolean> canReserveMap = new HashMap<>();
		String month = String.valueOf(totalDayCountAndNowMonthYear[1]);
		String year = String.valueOf(totalDayCountAndNowMonthYear[2]);
		System.out.println("year = " + year);
		System.out.println("month = " + month);

		if (optionalList.isEmpty()) {
			return null;
		}

		List<ScheduleDto> scheduleList = optionalList.get();
		Calendar calendar = Calendar.getInstance();
		ArrayList<LocalDate> list = new ArrayList<>();
		scheduleList.forEach(x -> list.add(x.getDate()));
		for (int i = 0; i < totalDayCount; i++) {
			try {
				Date date;
				if (String.valueOf(i).length() == 1) {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(
						year + "-" + month + "-" + 0 + "" + (i + 1));
				} else {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(
						year + "-" + month + "-" + (i + 1));
				}
				calendar.setTime(date);
				LocalDate localDate = LocalDate.ofInstant(calendar.toInstant(),
					ZoneId.systemDefault());

				canReserveMap.put(i + 1, !ListUtils.contains(list, localDate));
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return canReserveMap;
	}
}
