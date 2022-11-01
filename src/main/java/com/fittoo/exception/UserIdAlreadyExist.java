package com.fittoo.exception;

import com.fittoo.member.model.MemberInput;
import lombok.Getter;

@Getter
public class UserIdAlreadyExist extends RuntimeException{

	private MemberInput memberInput;

	public UserIdAlreadyExist() {
		super();
	}

	public UserIdAlreadyExist(String message) {
		super(message);
	}

	public UserIdAlreadyExist(String message, Throwable cause) {
		super(message, cause);
	}

	public UserIdAlreadyExist(Throwable cause) {
		super(cause);
	}

	protected UserIdAlreadyExist(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserIdAlreadyExist(String message, MemberInput input, Throwable cause) {
		super(message, cause);
		this.memberInput = input;
	}

	public UserIdAlreadyExist(String message, MemberInput input) {
		super(message);
		this.memberInput = input;
	}
}
