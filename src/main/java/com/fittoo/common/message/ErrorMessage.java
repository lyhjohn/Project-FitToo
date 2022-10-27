package com.fittoo.common.message;


public enum ErrorMessage {
    ALREADY_EXIST_USERID("해당 아이디는 이미 존재합니다."),
    INVALID_ID_OR_PWD("아이디 또는 비밀번호가 틀렸습니다."),

    INVALID_PROFILE_PICTURE("올바른 이미지를 등록해주세요."),

    INVALID_FILE("파일이 유효하지 않습니다"),

    ACCESS_REJECT("비정상적인 접근입니다."),

    NOT_FOUND_TRAINER("트레이너 정보 없음"),

    INVALID_DATE("올바른 날짜를 입력해주세요"),

    START_DAY_BIGGER_THAN_END_DAY("시작 날짜가 종료 날짜보다 클 수 없습니다.");


    private final String message;
    ErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
