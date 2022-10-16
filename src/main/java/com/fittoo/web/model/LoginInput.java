package com.fittoo.web.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginInput {
    @NotEmpty
    @NotNull
    private String loginId;

    @NotEmpty
    @NotNull
    private String password;

    @NotNull
    private String loginType;
}
