package com.fittoo.member.model;

public enum LoginType {
    ADMIN("관리자"),
    NORMAL("일반 회원"),
    TRAINER("트레이너");

    private final String description;
    LoginType(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
