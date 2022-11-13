package com.fittoo.common.message;


public enum ScheduleErrorMessage {


	INVALID_DATE("올바른 날짜를 입력해주세요"),

	START_DAY_BIGGER_THAN_END_DAY("시작 날짜가 종료 날짜보다 클 수 없습니다."),
	START_TIME_BIGGER_THAN_END_TIME("시작 시간이 종료 시간보다 클 수 없습니다."),

	CONTAINS_REGISTERED_DATE("이미 등록된 날짜가 포함되어 있습니다."),

	INVALID_SCHEDULE_INFO("트레이너의 스케줄 정보가 유효하지 않습니다."),

	EMPTY_SCHEDULE("해당 날짜에는 등록된 일정이 없습니다."),
	CAN_RESERVE_AFTER_NOW("현재 날짜(시간) 보다 이후의 스케줄만 등록할 수 있습니다."),
	ONLY_CANCEL_STATUS_CAN_DELETE("고객의 예약 보류 혹은 예약 완료 상태가 포함된 스케줄은 삭제할 수 없습니다.");

	private final String message;
	ScheduleErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}
}
