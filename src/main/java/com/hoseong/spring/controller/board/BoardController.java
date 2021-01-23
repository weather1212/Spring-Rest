package com.hoseong.spring.controller.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.service.board.BoardService;
import com.hoseong.spring.vo.board.BoardVO;

@Controller // 현재 클래스 빈 등록
@RequestMapping("/board/*")
public class BoardController {

	// 의존 관계 주입 => BoardServiceImpl 생성
	// IoC 의존관계 역전
	@Inject
	BoardService boardService;

	// 게시글 목록
	@RequestMapping("list")
	public ModelAndView list() throws Exception {
		List<BoardVO> list = boardService.listAll();

		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/list"); // 뷰를 list.jsp로 설정
		mav.addObject("list", list); // 데이터를 저장

		return mav; // lis.jsp 로 list 전달
	}

	// 게시글 작성 화면
	// @RequestMapping("board/write")
	// value = "", method = "전송방식"
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String write() {
		return "board/write"; // write.jsp로 이동
	}

	// 게시글 작성 처리
	@RequestMapping(value = "writeAction", method = RequestMethod.POST)
	public String writeAction(@ModelAttribute BoardVO vo) throws Exception {

		boardService.writeBoard(vo);

		return "redirect:list";
	}

	// 게시글 상세 내용 조회, 게시글 조회수 증가
	// @RequestMapping : get/post 방식으로 전달된 변수 1개
	// HttpSession 세션 객체
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int bno, HttpSession session) throws Exception {

		// 조회수 증가
		boardService.increaseViewcnt(bno, session);

		// 모델(데이터) + 뷰(화면)를 같이 전달
		ModelAndView mav = new ModelAndView();

		mav.setViewName("board/view"); // 뷰 이름
		mav.addObject("dto", boardService.readBoard(bno)); // 뷰에 전달할 데이터

		return mav;

	}

	// 게시글 수정
	// form에서 입력한 내용들은 @ModelAttribute BoardVO vo로 전달됨
	@RequestMapping(value = "updateAction", method = RequestMethod.POST)
	public String updateAction(@ModelAttribute BoardVO vo) throws Exception {

		boardService.updateBoard(vo);

		return "redirect:list";
	}
	
	//게시글 삭제
	@RequestMapping("delete")
	public String delete(@RequestParam int bno) throws Exception {
		boardService.deleteBoard(bno);
		
		return "redirect:list";
	}

}
