package com.fittoo.exception;

import com.fittoo.common.message.CommonErrorMessage;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{

	private CommonErrorMessage errorMessage;
	public AuthException() {
		super();
	}

	public AuthException(CommonErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthException(Throwable cause) {
		super(cause);
	}

	protected AuthException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
