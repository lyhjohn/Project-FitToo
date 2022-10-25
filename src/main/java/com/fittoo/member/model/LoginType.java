package com.fittoo.member.model;

public enum LoginType {
    ADMIN("관리자"),
    NORMAL("일반 회원"),
    TRAINER("트레이너");

    private final String memberType;
    LoginType(String memberType) {
        this.memberType = memberType;
    }

    public String memberType() {
        return memberType;
    }
}
