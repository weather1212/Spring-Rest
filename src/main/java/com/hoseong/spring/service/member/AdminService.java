package com.hoseong.spring.service.member;

import com.hoseong.spring.vo.member.MemberVO;

public interface AdminService {

	// 관리자 로그인 체크
	public String loginCheck(MemberVO vo);

}
