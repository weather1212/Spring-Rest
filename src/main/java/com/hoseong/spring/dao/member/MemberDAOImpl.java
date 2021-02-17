package com.hoseong.spring.dao.member;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoseong.spring.vo.member.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	// SqlSession 객체를 스프링에서 생성하여 주입
	// 의존관계 주입, 느슨한 결합
	@Autowired
	private SqlSession sqlSession;

	// 회원 로그인 체크
	@Override
	public boolean loginCheck(MemberVO vo) {
		String name = sqlSession.selectOne("memberMapper.loginCheck", vo);
		return (name == null) ? false : true; // 조건?참일때:거짓일때
	}

	// 회원 로그인 정보
	@Override
	public MemberVO viewMember(MemberVO vo) {
		return sqlSession.selectOne("memberMapper.viewMember", vo);
	}

	// 회원 로그아웃
	@Override
	public void logout(HttpSession session) {
	}

	// 회원 가입
	@Override
	public int join(MemberVO vo) {
		
		System.out.println(vo);
		
		return sqlSession.insert("memberMapper.join", vo) ;
	}

	//아이디 중복체크
	@Override
	public int idCheck(String userId) {
		System.out.println("중복확인 아이디 service -> dao : " + userId);
		return sqlSession.selectOne("memberMapper.idCheck", userId);
	}
}
