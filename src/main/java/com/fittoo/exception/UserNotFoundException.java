package com.fittoo.exception;

import com.fittoo.common.message.CommonErrorMessage;
import com.fittoo.common.message.FindErrorMessage;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{

	private CommonErrorMessage errorMessage;
	private FindErrorMessage findErrorMessage;
	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(CommonErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public UserNotFoundException(FindErrorMessage findErrorMessage) {
		this.findErrorMessage = findErrorMessage;
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}

	protected UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
