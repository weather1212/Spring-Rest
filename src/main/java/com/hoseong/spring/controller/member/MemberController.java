package com.hoseong.spring.controller.member;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.service.member.MemberService;
import com.hoseong.spring.vo.member.MemberVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Inject
	MemberService memberService;

	// 로그인 화면
	@RequestMapping("login")
	public String login() {
		return "member/login"; // view/member/login.jsp로 이동
	}

	// 로그인 처리
	@RequestMapping("loginCheck")
	public ModelAndView loginCheck(@ModelAttribute MemberVO vo, HttpSession session) {
		boolean result = memberService.loginCheck(vo, session);

		ModelAndView mav = new ModelAndView();

		if (result == true) { // 로그인 성공 시
			mav.setViewName("home"); // 홈으로 이동
			mav.addObject("msg", "success");
		} else { // 로그인 실패 시
			mav.setViewName("member/login"); // 로그인 페이지로 이동
			mav.addObject("msg", "failure");
		}
		return mav;
	}

	// 로그아웃 처리
	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session) {
		memberService.logout(session);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("member/login"); // 로그인 페이지로 이동
		mav.addObject("msg", "logout");

		return mav;
	}

}
