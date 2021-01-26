package com.hoseong.spring.service.member;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.hoseong.spring.dao.board.BoardDAO;
import com.hoseong.spring.vo.board.BoardVO;
import com.hoseong.spring.vo.member.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	private BoardDAO boardDAO;

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
