package com.fittoo.trainer.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nonapi.io.github.classgraph.json.JSONDeserializer;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

	@Id
	@GeneratedValue
	private Long id;

	private String comment;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainer_id")
	private Trainer trainer;

	private LocalDate date;

	private Time startTime;
	private Time endTime;

	public Schedule(Calendar date) {
		this.date = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}
}
