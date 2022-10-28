package com.fittoo.member.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateInput {

	private String userId;
	private String phoneNumber;
	private String userName;
	private int gender;
	private String exercisePeriod;
	private List<String> regPurposeList;
	private String addr;
	private String addDetail;
	private String zipcode;
}
