package com.fittoo.common.message;

import org.junit.jupiter.api.Test;

class ErrorMessageTest {

    @Test
    void errorMessageTest() {
        ErrorMessage message = ErrorMessage.ALREADY_EXIST_USERID;
        System.out.println(message.name());
        System.out.println(message.message());
    }
}