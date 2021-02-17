package com.hoseong.spring.controller.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.service.member.AdminService;
import com.hoseong.spring.vo.member.MemberVO;

@Controller
@RequestMapping("admin/*")
public class AdminController {

	@Autowired
	AdminService adminService;

	// 로그인 화면
	@RequestMapping("login")
	public String login() {
		return "admin/adminLogin"; // views/admin/adminLogin.jsp로 이동
	}

	// 로그인 처리
	@RequestMapping("loginCheck")
	public ModelAndView loginCheck(@ModelAttribute MemberVO vo,  ModelAndView mav, HttpSession session) {
		String name = adminService.loginCheck(vo);

		if (name != null) { // 로그인 성공 시
			session.setAttribute("adminId", vo.getUserId());
			session.setAttribute("userId", vo.getUserId());
			session.setAttribute("adminName", name);
			session.setAttribute("userName", name);
			
			mav.setViewName("adminHome"); // 홈으로 이동
			mav.addObject("msg", "success");
		} else { // 로그인 실패 시
			mav.setViewName("admin/adminLogin"); // 로그인 페이지로 이동
			mav.addObject("msg", "failure");
		}
		return mav;
	}

	// 로그아웃 처리
	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		
		ModelAndView mav = new ModelAndView();

		mav.setViewName("admin/adminLogin"); // 로그인 페이지로 이동
		mav.addObject("msg", "logout");

		return mav;
	}

}
