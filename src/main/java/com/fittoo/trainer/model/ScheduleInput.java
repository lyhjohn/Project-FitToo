package com.fittoo.trainer.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ScheduleInput {

	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private String comment;
	private int personnel;
	private long price;
}
