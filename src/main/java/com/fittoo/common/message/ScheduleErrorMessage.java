package com.fittoo.common.message;


public enum ScheduleErrorMessage {


	INVALID_DATE("올바른 날짜를 입력해주세요"),

	START_DAY_BIGGER_THAN_END_DAY("시작 날짜가 종료 날짜보다 클 수 없습니다."),
	START_TIME_BIGGER_THAN_END_TIME("시작 시간이 종료 시간보다 클 수 없습니다."),

	CONTAINS_REGISTERED_DATE("이미 등록된 날짜가 포함되어 있습니다.");

	private final String message;
	ScheduleErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}
}
