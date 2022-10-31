package com.fittoo.common.message;


public enum CommonErrorMessage {

	ACCESS_REJECT("비정상적인 접근입니다.");



	private final String message;
	CommonErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}
}
