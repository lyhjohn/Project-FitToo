package com.fittoo.common.message;


public enum CommonErrorMessage {

	ACCESS_REJECT("비정상적인 접근입니다."),

	NOT_FOUND_USER("회원 정보를 조회할 수 없습니다. 다시 로그인 해 주세요."),

	ABNORMAL_APPROACH("비정상적인 접근입니다.");



	private final String message;
	CommonErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}
}
