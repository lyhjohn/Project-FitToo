package com.fittoo.exception;

import com.fittoo.common.message.RegisterErrorMessage;
import com.fittoo.member.model.MemberInput;
import com.fittoo.trainer.model.TrainerInput;
import lombok.Getter;

@Getter
public class RegisterException extends RuntimeException{

	private MemberInput memberInput;
	private TrainerInput trainerInput;
	private String loginType;
	private RegisterErrorMessage errorMessage;


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

	public RegisterException(RegisterErrorMessage errorMessage, MemberInput input, String loginType, Throwable cause) {
		super(cause);
		this.errorMessage = errorMessage;
		this.memberInput = input;
		this.loginType = loginType;
	}

	public RegisterException(RegisterErrorMessage errorMessage, TrainerInput input, String loginType, Throwable cause) {
		super(cause);
		this.errorMessage = errorMessage;
		this.trainerInput = input;
		this.loginType = loginType;
	}

	public RegisterException(String message, TrainerInput input, String loginType) {
		super(message);
		this.trainerInput = input;
		this.loginType = loginType;
	}

	public RegisterException(RegisterErrorMessage errorMessage, MemberInput input, String loginType) {
		this.errorMessage = errorMessage;
		this.memberInput = input;
		this.loginType = loginType;
	}
}
