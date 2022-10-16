package com.fittoo.common.model;

import com.fittoo.common.message.ErrorMessage;
import lombok.Getter;

@Getter
public class ServiceResult {
    private final boolean result;

    private final ErrorMessage errorMessage;


    public ServiceResult(boolean result, ErrorMessage errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public ServiceResult() {
        this.result = true;
        this.errorMessage = null;
    }
}
