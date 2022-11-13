package com.fittoo.withdraw_user.entity;

import com.fittoo.member.model.LoginType;
import com.fittoo.withdraw_user.constant.WithdrawStatus;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawUser extends DateBaseEntity{

	@Id
	String id;

	LoginType loginType;

	@Enumerated(EnumType.STRING)
	WithdrawStatus status;


	public WithdrawUser(String id, LoginType loginType) {
		this.id = id;
		this.loginType = loginType;
		status = WithdrawStatus.HOLD_WITHDRAW;
	}

	public void updateStatus() {
		this.status = WithdrawStatus.COMPLETE_WITHDRAW;
	}
}
