package com.fittoo.exception;

import com.fittoo.common.message.ReservationErrorMessage;
import com.fittoo.member.model.LoginType;
import lombok.Getter;

@Getter
public class ReservationException extends RuntimeException{

	private String memberId;
	private Long reservationId;
	private LoginType loginType;
	private ReservationErrorMessage errorMessage;
	public ReservationException() {
		super();
	}

	public ReservationException(ReservationErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ReservationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReservationException(Throwable cause) {
		super(cause);
	}

	protected ReservationException(String errorMessage, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(errorMessage, cause, enableSuppression, writableStackTrace);
	}

	public ReservationException(ReservationErrorMessage errorMessage, LoginType loginType, String memberId, Long reservationId) {
		this.errorMessage = errorMessage;
		this.memberId = memberId;
		this.reservationId = reservationId;
		this.loginType = loginType;
	}

	public ReservationException(String message, String memberId, Long reservationId) {
		super(message);
		this.memberId = memberId;
		this.reservationId = reservationId;
	}

	public ReservationException(ReservationErrorMessage errorMessage, String memberId, Long reservationId) {
		this.errorMessage = errorMessage;
		this.memberId = memberId;
		this.reservationId = reservationId;
	}
}
