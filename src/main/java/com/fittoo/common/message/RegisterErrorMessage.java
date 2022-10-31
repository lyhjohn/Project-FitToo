package com.fittoo.common.message;


public enum RegisterErrorMessage {
	ALREADY_EXIST_USERID("해당 아이디는 이미 존재합니다."),

	Pwd_And_RePwd_Not_Equal("비밀번호와 비밀번호 확인이 일치하지 않습니다.");

	private final String message;
	RegisterErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}
}
