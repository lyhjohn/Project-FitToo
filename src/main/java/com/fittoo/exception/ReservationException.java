package com.fittoo.exception;

public class ReservationException extends RuntimeException{

	public ReservationException() {
		super();
	}

	public ReservationException(String message) {
		super(message);
	}

	public ReservationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReservationException(Throwable cause) {
		super(cause);
	}

	protected ReservationException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
