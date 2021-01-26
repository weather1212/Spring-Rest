package com.hoseong.spring.dao.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.hoseong.spring.vo.board.BoardVO;
import com.hoseong.spring.vo.member.MemberVO;

public interface MemberDAO {

	// 로그인 체크
	public boolean loginCheck(MemberVO vo, HttpSession session);

	// 회원 로그인 정보
	public MemberVO viewMember(MemberVO vo);

	// 회원 로그아웃
	public void logout(HttpSession session);


}
