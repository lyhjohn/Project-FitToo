package com.fittoo.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SearchType {
	ADDRESS("address"),
	TRAINER_NAME("trainerName");

	private final String search;

	public String search() {
		return this.search;
	}
}
