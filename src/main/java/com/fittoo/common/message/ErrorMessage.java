package com.fittoo.common.message;


public enum ErrorMessage {
    ALREADY_EXIST_USERID("해당 아이디는 이미 존재합니다."),
    INVALID_ID_OR_PWD("아이디 또는 비밀번호가 틀렸습니다."),

    INVALID_PROFILE_PICTURE("올바른 이미지를 등록해주세요."),

    INVALID_FILE("파일이 유효하지 않습니다"),

    ACCESS_REJECT("비정상적인 접근입니다."),

    NOT_FOUND_TRAINER("트레이너 정보 없음");

    private final String description;
    ErrorMessage(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
