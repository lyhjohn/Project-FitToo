package com.fittoo.common.textEnum;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType {

	NORMAL("일반 회원"),
	TRAINER("트레이너");

	private final String memberType;

//	public String memberType() {
//		return memberType;
//	}

}
