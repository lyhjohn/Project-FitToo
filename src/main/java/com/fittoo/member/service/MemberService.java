package com.fittoo.member.service;

import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.model.MemberUpdateInput;

public interface MemberService {

    void memberRegister(MemberInput memberInput);

    MemberDto findMember(String userId);

    void update(MemberUpdateInput input, String userId);
}
