package com.fittoo.common.message;

public enum ReservationErrorMessage {

	EMPTY_SCHEDULE("선택하신 날짜에는 일정이 없습니다.");



	private final String message;
	ReservationErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}

}
