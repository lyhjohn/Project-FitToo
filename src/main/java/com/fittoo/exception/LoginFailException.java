package com.fittoo.exception;

import com.fittoo.common.message.LoginErrorMessage;
import lombok.Getter;

@Getter
public class LoginFailException extends RuntimeException{

	private LoginErrorMessage errorMessage;
	public LoginFailException() {
	}

	public LoginFailException(LoginErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LoginFailException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginFailException(Throwable cause) {
		super(cause);
	}

	public LoginFailException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
