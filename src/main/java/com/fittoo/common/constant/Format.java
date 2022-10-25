package com.fittoo.common.constant;

import java.time.format.DateTimeFormatter;

public abstract class Format {


	public static DateTimeFormatter dateTimePattern(String format) {
		return DateTimeFormatter.ofPattern(format);
	}
}
