package com.hoseong.spring.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.service.board.BoardPagination;
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
	// @RequestParam(defaultValue = "") ==> 파라미터가 넘어오지않을 시 ""를 기본값으로 할당
	public ModelAndView list(@RequestParam(defaultValue = "title") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage) throws Exception {
		
		//레코드의 개수 계산
		int count = boardService.countArticle(searchOption, keyword);
		
		//페이징 처리
		BoardPagination boardPagination = new BoardPagination(count, curPage);
		int start = boardPagination.getPageBegin();
		int end = boardPagination.getPageEnd();
		
		List<BoardVO> list = boardService.listAll(start, end, searchOption, keyword);

		System.out.println("현재 페이지 : " + curPage);
		System.out.println("조건 : " + searchOption);
		System.out.println("키워드 : " + keyword);
		System.out.println("레코드 개수 : " + count);

		ModelAndView mav = new ModelAndView();

//		mav.addObject("list", list);
//		mav.addObject("count", count);
//		mav.addObject("searchOption", searchOption);
//		mav.addObject("keyword", keyword);

		// 데이터를 map으로 묶어서 저장
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("list", list); // list
		map.put("count", count); // 레코드의 개수
		map.put("searchOption", searchOption); // 검색 옵션
		map.put("keyword", keyword); // 검색 키워드
		map.put("boardPagination", boardPagination);

		mav.addObject("map", map); // map에 저장된 데이터를 mav에 저장
		mav.setViewName("board/list"); // 뷰를 list.jsp로 설정

		return mav; // lis.jsp 로 list 전달 (뷰에서 ${list} 사용)
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
	public String writeAction(@ModelAttribute BoardVO vo, HttpSession session) throws Exception {
		// session에 저장된 userId를 writer에 저장
		String writer = (String) session.getAttribute("userId");
		// vo에 writer 세팅
		vo.setWriter(writer);
		boardService.writeBoard(vo);

		return "redirect:list";
	}

	// 게시글 상세 내용 조회, 게시글 조회수 증가
	// @RequestMapping : get/post 방식으로 전달된 변수 1개
	// HttpSession 세션 객체
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int bno, @RequestParam(defaultValue = "title") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage, HttpSession session) throws Exception {
		// 조회수 증가
		boardService.increaseViewcnt(bno, session);

		// 모델(데이터) + 뷰(화면)를 같이 전달
		ModelAndView mav = new ModelAndView();

		mav.setViewName("board/view"); // 뷰 이름
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("curPage", curPage);
		
		mav.addObject("map", map); // 뷰에 전달할 데이터
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

	// 게시글 삭제
	@RequestMapping("delete")
	public String delete(@RequestParam int bno) throws Exception {
		boardService.deleteBoard(bno);

		return "redirect:list";
	}

}
