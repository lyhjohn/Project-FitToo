package com.fittoo.exception;

import com.fittoo.common.message.WithdrawErrorMessage;
import lombok.Getter;

@Getter
public class WithdrawException extends RuntimeException{

	private WithdrawErrorMessage errorMessage;
	public WithdrawException() {
		super();
	}

	public WithdrawException(WithdrawErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public WithdrawException(String message, Throwable cause) {
		super(message, cause);
	}

	public WithdrawException(Throwable cause) {
		super(cause);
	}

	protected WithdrawException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
