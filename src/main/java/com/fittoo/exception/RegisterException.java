package com.fittoo.exception;

import com.fittoo.member.model.MemberInput;
import com.fittoo.trainer.model.TrainerInput;
import lombok.Getter;

@Getter
public class RegisterException extends RuntimeException{

	private MemberInput memberInput;
	private TrainerInput trainerInput;


	public RegisterException() {
		super();
	}

	public RegisterException(String message) {
		super(message);
	}

	public RegisterException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegisterException(Throwable cause) {
		super(cause);
	}

	protected RegisterException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RegisterException(String message, MemberInput input, Throwable cause) {
		super(message, cause);
		this.memberInput = input;
	}

	public RegisterException(String message, TrainerInput input, Throwable cause) {
		super(message, cause);
		this.trainerInput = input;
	}

	public RegisterException(String message, TrainerInput input) {
		super(message);
		this.trainerInput = input;
	}

	public RegisterException(String message, MemberInput input) {
		super(message);
		this.memberInput = input;
	}
}
