package com.fittoo.exception;

import com.fittoo.common.message.FileErrorMessage;
import com.fittoo.trainer.model.TrainerInput;
import lombok.Getter;

@Getter
public class FileException extends RuntimeException {

	private FileErrorMessage errorMessage;

	public FileException() {
		super();
	}

	public FileException(FileErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public FileException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileException(Throwable cause) {
		super(cause);
	}

	protected FileException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileException(String message, TrainerInput input) {
		super(message);
	}
}
