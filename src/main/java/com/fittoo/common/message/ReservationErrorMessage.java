package com.fittoo.common.message;

public enum ReservationErrorMessage {

	EMPTY_SCHEDULE("선택하신 날짜에는 일정이 없습니다."),
	INVALID_TRAINER_INFO("트레이너 정보가 유효하지 않습니다."),
	EXIST_SAME_RESERVATION("동일한 예약이 존재합니다."),
	FULL_RESERVATION("정원이 가득 찼습니다.");



	private final String message;
	ReservationErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}

}
