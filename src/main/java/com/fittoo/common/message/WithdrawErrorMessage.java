package com.fittoo.common.message;

import lombok.Getter;

public enum WithdrawErrorMessage {

	EXIST_RESERVATION("현재 대기 중인 예약 취소 후 탈퇴 바랍니다.");

	private final String message;

	WithdrawErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return this.message;
	}
}
