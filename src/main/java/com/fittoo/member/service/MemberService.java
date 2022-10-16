package com.fittoo.member.service;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.member.model.MemberInput;

public interface MemberService {

    ServiceResult memberRegister(MemberInput memberInput);
}
