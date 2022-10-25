package com.fittoo.utills;

import static org.assertj.core.api.Assertions.*;

import com.fittoo.trainer.entity.CantReserveDate;
import com.fittoo.trainer.repository.CantReserveDateRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DeduplicationUtilsTest {

	@Autowired
	CantReserveDateRepository cantReserveDateRepository;

	@Test
	void CalendarTest() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, 10, 23);
		Date time = calendar.getTime();
		int i = calendar.get(Calendar.YEAR);
		System.out.println("i = " + i);
		int i1 = calendar.get(Calendar.MONTH);
		System.out.println("i1 = " + i1);
		int i2 = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("i2 = " + i2);

	}


	@Test
	void dateTest() {
		String year = String.valueOf(2022);
		String month = String.valueOf(12);
		String day = String.valueOf(13);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String strDate = year + "-" + month + "-" + day;
		System.out.println("strDate = " + strDate);

//		LocalDate parse = LocalDate.parse(strDate, formatter);
		LocalDate parse = LocalDate.parse(strDate, formatter);
		System.out.println("parse = " + parse);
	}


}