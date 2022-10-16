package com.fittoo.common.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorMessageTest {

    @Test
    void errorMessageTest() {
        ErrorMessage message = ErrorMessage.ALREADY_EXIST_USERID;
        System.out.println(message.name());
        System.out.println(message.description());
    }
}