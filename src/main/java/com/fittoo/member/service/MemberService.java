package com.fittoo.member.service;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.MemberInput;

public interface MemberService {

    ServiceResult memberRegister(MemberInput memberInput);

    MemberDto findMember(String userId);
}
