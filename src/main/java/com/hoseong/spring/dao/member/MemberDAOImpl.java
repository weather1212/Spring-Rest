package com.hoseong.spring.dao.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hoseong.spring.vo.board.BoardVO;
import com.hoseong.spring.vo.member.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Inject
	private SqlSession sqlSession;

	@Override
	public boolean loginCheck(MemberVO vo, HttpSession session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberVO viewMember(MemberVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout(HttpSession session) {
		// TODO Auto-generated method stub

	}
}
