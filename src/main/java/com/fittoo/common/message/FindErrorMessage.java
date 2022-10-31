package com.fittoo.common.message;


public enum FindErrorMessage {
	NOT_FOUND_TRAINER("트레이너 정보 없음"),

	NOT_FOUND_USER("회원 정보를 조회할 수 없습니다.");


	private final String message;
	FindErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}
}
