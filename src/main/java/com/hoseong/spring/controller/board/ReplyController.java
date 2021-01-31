package com.hoseong.spring.controller.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.service.board.ReplyPagination;
import com.hoseong.spring.service.board.ReplyService;
import com.hoseong.spring.vo.board.ReplyVO;

// REST : Representational State Transfer
// 하나의 URI가 하나의 고유한 리소스를 대표하도록 설계된 개념

// http://localhost/spring/board/list?bno=1 ==> url+파라미터(bno)
// http://localhost/spring/board/list/1 ==> url
// RestController는 스프링 4.0부터 지원
// @Controller, @RestController 차이점은 메서드가 종료되면 화면전환의 유무
@RestController
@RequestMapping("/reply/*")
public class ReplyController {

	@Inject
	ReplyService replyService;

	// 댓글 입력(단순 string 교환)
	@RequestMapping("write")
	public void write(@ModelAttribute ReplyVO vo, HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		vo.setReplyer(userId);
		// 입력된 댓글 로그출력
		System.out.println(vo);

		replyService.write(vo);
	}

	// **REST방식으로 댓글 입력(json 전달하여 글쓰기)
	// @ResponseEntity : 데이터 + http status code
	// @ResponseBody : 객체를 json으로 (json - String)
	// @RequestBody : json을 객체로
	@RequestMapping(value = "writeRest", method = RequestMethod.POST)
	public ResponseEntity<String> writeRest(@RequestBody ReplyVO vo, HttpSession session) {
		ResponseEntity<String> entity = null;
		try {
			String userId = (String) session.getAttribute("userId");
			vo.setReplyer(userId);
			// 입력된 댓글 로그
			System.out.println(vo);
			replyService.write(vo);

			// 댓글 입력이 성공하면 성공메시지 저장
			entity = new ResponseEntity<String>("success", HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			// 댓글 입력이 성공하면 성공메시지 저장
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// 입력 처리 HTTP 상태 메시지 리턴
		return entity;
	}

	// 댓글 목록(@Controller방식 : view(화면)를 리턴)
	@RequestMapping("list")
	public ModelAndView list(@RequestParam int bno, @RequestParam(defaultValue = "1") int curPage, ModelAndView mav, HttpSession session) {
		// ** 페이징 처리
		int count = replyService.count(bno);	//댓글 개수
		ReplyPagination replyPagination = new ReplyPagination(count, curPage);
		int start = replyPagination.getPageBegin();
		int end = replyPagination.getPageEnd();
		List<ReplyVO> list = replyService.list(bno, start, end, session);
		
		// 뷰이름 지정
		mav.setViewName("board/replyList");
		// 뷰에 전달할 데이터 지정
		mav.addObject("list", list);
		mav.addObject("replyPagination", replyPagination);

		// replyList.jsp로 포워딩
		return mav;
	}

	// 댓글 목록(@RestController Json 방식으로 처리 : 데이터를 리턴)
	@RequestMapping("listJson")
	@ResponseBody
	public List<ReplyVO> listJson(@RequestParam int bno, @RequestParam(defaultValue = "1") int curPage, ModelAndView mav, HttpSession session) {
		int count = replyService.count(bno);	//댓글 개수
		ReplyPagination replyPagination = new ReplyPagination(count, curPage);
		int start = replyPagination.getPageBegin();
		int end = replyPagination.getPageEnd();
		List<ReplyVO> list = replyService.list(bno, start, end, session);
		return list;
	}

}
