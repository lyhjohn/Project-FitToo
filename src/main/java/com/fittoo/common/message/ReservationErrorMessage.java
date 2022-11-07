package com.fittoo.common.message;

public enum ReservationErrorMessage {

	EMPTY_SCHEDULE("선택하신 날짜에는 일정이 없습니다."),
	INVALID_TRAINER_INFO("트레이너 정보가 유효하지 않습니다."),
	EXIST_SAME_RESERVATION("동일한 예약이 존재합니다."),
	FULL_RESERVATION("정원이 가득 찼습니다."),
	INVALID_RESERVATION("예약 정보가 유효하지 않습니다."),
	ALREADY_COMPLETED_RESERVATION("이미 확인된 예약입니다."),
	ALREADY_CANCELED_RESERVATION("이미 취소된 예약입니다."),
	PROHIBIT_RESERVATION_CANCEL_THREE_DAYS_AGO("예약 날짜로 부터 삼일 이내 취소할 수 없습니다. 날짜 변경을 요청하세요.");


	private final String message;

	ReservationErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}

}
