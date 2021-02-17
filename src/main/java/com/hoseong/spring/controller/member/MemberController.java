package com.hoseong.spring.controller.member;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.service.member.MemberService;
import com.hoseong.spring.vo.member.MemberVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
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
			mav.setViewName("redirect:/"); // 홈으로 이동
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

	// 회원가입 화면
	@RequestMapping("join")
	public String join() {
		return "member/join";
	}

	// 회원가입 처리
	@RequestMapping(value = "joinAction", method = RequestMethod.POST)
	public ModelAndView joinAction(@ModelAttribute MemberVO vo, HttpSession session) {
		boolean result = memberService.join(vo);

		ModelAndView mav = new ModelAndView();

		if (result == true) { // 회원가입 성공 시
			mav.setViewName("member/login"); // 로그인페이지로 이동
			mav.addObject("msg", "success");
		} else { // 회원가입 실패 시
			mav.setViewName("member/join"); // 회원가입 페이지로 이동
			mav.addObject("msg", "failure");
		}
		return mav;
	}

	// 아이디 중복 확인
	@RequestMapping(value = "idCheck", method = RequestMethod.GET)
	@ResponseBody
	public int idCheck(@RequestParam("userId") String userId) {
		System.out.println("중복확인 아이디 jsp->controller : " + userId);
		return memberService.idCheck(userId);
	}

}
