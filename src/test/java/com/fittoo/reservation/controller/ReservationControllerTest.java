package com.fittoo.reservation.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.fittoo.reservation.service.ReservationService;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReservationControllerTest {

	@Autowired
	ReservationController reservationController;

	@Autowired
	ReservationService reservationService;

	@Test
	void getDateTest() {
	    //given
		LocalDate date = reservationController.getDate(2020, 12, 25);
		//when & then
		assertThat(date).isEqualTo("2020-12-25");
	}
}