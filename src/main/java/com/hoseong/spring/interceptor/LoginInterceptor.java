package com.hoseong.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	// 요청 전 실행
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 세션 객체  생성
		HttpSession session = request.getSession();
		
		// 세션에 id가 null이면
		if(session.getAttribute("userId") == null) {
			// 로그인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/member/login");
			
			// 컨트롤러를 실행하지 않는다. (요청 페이지로 이동하지 않는다.)
			return false;
		} else {	// 세션 id가 null이 아니면
			// 컨트롤러 실행 (요청페이지로 이동)
			return true;
		}
	}
	
	// 요청 후 실행
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	

	
}
