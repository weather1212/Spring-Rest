package com.hoseong.spring.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoseong.spring.dao.member.AdminDAO;
import com.hoseong.spring.vo.member.MemberVO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDAO adminDAO;
	
	// 관리자 로그인체크
	@Override
	public String loginCheck(MemberVO vo) {
		return adminDAO.loginCheck(vo);
	}

}
