package com.fittoo.exception;

import com.fittoo.common.message.ReviewErrorMessage;
import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException{

	private ReviewErrorMessage errorMessage;
	public ReviewException() {
		super();
	}

	public ReviewException(ReviewErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ReviewException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReviewException(Throwable cause) {
		super(cause);
	}

	protected ReviewException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
