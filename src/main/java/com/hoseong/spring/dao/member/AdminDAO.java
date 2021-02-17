package com.hoseong.spring.dao.member;

import com.hoseong.spring.vo.member.MemberVO;

public interface AdminDAO {

	// 관리자 로그인 체크
	public String loginCheck(MemberVO vo);

}
