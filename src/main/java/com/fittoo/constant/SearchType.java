package com.fittoo.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SearchType {
	ADDRESS("address"),
	TRAINER_NAME("trainerName"),

	ALL_TYPE("all");

	private final String search;

	public String search() {
		return this.search;
	}
}
