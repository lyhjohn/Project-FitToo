package com.fittoo.member.service;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.model.MemberUpdateInput;

public interface MemberService {

    ServiceResult memberRegister(MemberInput memberInput);

    MemberDto findMember(String userId);

    void update(MemberUpdateInput input, String userId);
}
