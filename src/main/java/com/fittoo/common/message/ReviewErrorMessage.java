package com.fittoo.common.message;

public enum ReviewErrorMessage {

	ALREADY_EXIST_REVIEW("이미 등록한 리뷰가 존재합니다."),
	NOT_FOUND_REVIEW("해당하는 리뷰가 존재하지 않습니다.");

	private String message;

	ReviewErrorMessage(String message) {
		this.message = message;
	}

	public String message() {
		return this.message;
	}
}
