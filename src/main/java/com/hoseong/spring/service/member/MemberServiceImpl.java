package com.hoseong.spring.service.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoseong.spring.dao.member.MemberDAO;
import com.hoseong.spring.vo.member.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDao;

	// 회원 로그인 체크
	@Override
	public boolean loginCheck(MemberVO vo, HttpSession session) {
		boolean result = memberDao.loginCheck(vo);

		if (result) { // true일 경우 세션에 등록
			MemberVO vo2 = viewMember(vo);

			// 세션 변수 등록
			session.setAttribute("userId", vo2.getUserId());
			session.setAttribute("userName", vo2.getUserName());
		}
		return result;
	}

	// 회원 로그인 정보
	@Override
	public MemberVO viewMember(MemberVO vo) {
		return memberDao.viewMember(vo);
	}

	// 회원 로그아웃
	@Override
	public void logout(HttpSession session) {
		// 세션 개별 삭제
//		session.removeAttribute("userId");
		// 세션 정보를 초기화
		session.invalidate();
	}
	
	// 회원 가입
	@Override
	public boolean join(MemberVO vo) {
		
		if (memberDao.join(vo) == 1) {
			return true;
		}
		return false;
	}

	//아이디 중복체크
	@Override
	public int idCheck(String userId) {
		return memberDao.idCheck(userId);
	}

}
