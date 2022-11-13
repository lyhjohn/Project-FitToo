package com.fittoo.common.message;


public enum LoginErrorMessage {

	NOT_FOUND_TRAINER("트레이너 정보 없음"),

	NOT_FOUND_USER("회원 정보를 조회할 수 없습니다."),

	INVALID_ID_OR_PWD("아이디 혹은 비밀번호가 틀렸습니다"),
	WITHDRAW_USER("탈퇴한 회원입니다.");


	private final String message;
	LoginErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}
}
