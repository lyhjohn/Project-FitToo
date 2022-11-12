package com.fittoo.member.service;

import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.MemberInput;
import com.fittoo.trainer.model.UpdateInput;

public interface MemberService {

    void memberRegister(MemberInput memberInput);

    MemberDto findMember(String userId);

    MemberDto update(UpdateInput input);

	void withdraw(String userId);

	boolean existWithdrawUser(String userId);

	void completeWithdraw(String id);
}
