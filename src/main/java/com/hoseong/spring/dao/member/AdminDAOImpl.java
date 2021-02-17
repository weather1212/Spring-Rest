package com.hoseong.spring.dao.member;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoseong.spring.vo.member.MemberVO;

@Repository
public class AdminDAOImpl implements AdminDAO {

	@Autowired
	SqlSession sqlSession;
	
	// 관리자 로그인체크
	@Override
	public String loginCheck(MemberVO vo) {
		return sqlSession.selectOne("adminMapper.loginCheck", vo);
	}

}
