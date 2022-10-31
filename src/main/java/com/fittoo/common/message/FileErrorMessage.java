package com.fittoo.common.message;


public enum FileErrorMessage {

	INVALID_PROFILE_PICTURE("올바른 이미지를 등록해주세요."),

	INVALID_FILE("파일이 유효하지 않습니다");

	private final String message;
	FileErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}
}
