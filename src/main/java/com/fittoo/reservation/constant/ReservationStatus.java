package com.fittoo.reservation.constant;

public enum ReservationStatus {

	HOLD("보류"),
	CANCEL("취소"),
	COMPLETE("예약 완료"),
	END("수업 완료");
	private final String status;

	ReservationStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
