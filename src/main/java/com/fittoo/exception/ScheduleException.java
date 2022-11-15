package com.fittoo.exception;

import com.fittoo.common.message.ScheduleErrorMessage;
import lombok.Getter;

@Getter
public class ScheduleException extends RuntimeException{

	private ScheduleErrorMessage errorMessage;
	public ScheduleException() {
	}

	public ScheduleException(ScheduleErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ScheduleException(ScheduleErrorMessage errorMessage, Throwable cause) {
		super(cause);
		this.errorMessage = errorMessage;
	}

	public ScheduleException(Throwable cause) {
		super(cause);
	}

	public ScheduleException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
