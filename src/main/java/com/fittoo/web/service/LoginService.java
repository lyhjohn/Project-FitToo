package com.fittoo.web.service;

import com.fittoo.web.model.LoginInput;
import com.fittoo.web.model.LoginInfo;

public interface LoginService {
    LoginInfo loginSubmit(LoginInput input);
}
