package com.hoseong.spring.controller.board;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	@Autowired
	ReplyService replyService;

	// 1_1. 댓글 입력(파라미터 전달 방식)
	@RequestMapping("write")
	public void write(@ModelAttribute ReplyVO vo, HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		vo.setReplyer(userId);
		// 입력된 댓글 로그출력
		System.out.println(vo);

		replyService.write(vo);
	}

	// 1_2. **REST방식으로 댓글 입력(json 전달하여 글쓰기)
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

	// 댓글 목록 ==================================================================
	// 2_1. 댓글 목록(@Controller방식 : view(화면)를 리턴)
	@RequestMapping("list")
	public ModelAndView list(@RequestParam int bno, @RequestParam(defaultValue = "1") int curPage, ModelAndView mav,
			HttpSession session) {
		// ** 페이징 처리
		int count = replyService.count(bno); // 댓글 개수
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

	// 2_2. 댓글 목록(@RestController Json 방식으로 처리 : 데이터를 리턴)
	@RequestMapping("listJson")
	@ResponseBody
	public List<ReplyVO> listJson(@RequestParam int bno, @RequestParam(defaultValue = "1") int curPage,
			ModelAndView mav, HttpSession session) {
		int count = replyService.count(bno); // 댓글 개수
		ReplyPagination replyPagination = new ReplyPagination(count, curPage);
		int start = replyPagination.getPageBegin();
		int end = replyPagination.getPageEnd();
		List<ReplyVO> list = replyService.list(bno, start, end, session);
		return list;
	}

	// **Controller 추가 사항 - Rest 방식으로 댓글 목록, 수정, 삭제 처리
	// =======================@PathVariable, RequestMethod.GET/PUT/PATCH/DELETE
	// 2_3. 댓글 목록 (@RestController방식 : json으로 전달하여 목록생성)
	// /reply/list/1 => 1번 게시물의 댓글 목록 리턴
	// /reply/list/2 => 2번 게시물의 댓글 목록 리턴
	// @PathVariable : url에 입력될 변수값 지정
	@RequestMapping(value = "/list/{bno}/{curPage}", method = RequestMethod.GET)
	public ModelAndView replyList(@PathVariable("bno") int bno, @PathVariable("curPage") int curPage, ModelAndView mav,
			HttpSession session) {
		// 페이징 처리
		int count = replyService.count(bno); // 댓글 개수
		ReplyPagination replyPagination = new ReplyPagination(count, curPage);
		// 현재 페이지의 페이징 시작 번호
		int start = replyPagination.getPageBegin();
		// 현재 페이지의 페이징 끝 번호
		int end = replyPagination.getPageEnd();
		List<ReplyVO> list = replyService.list(bno, start, end, session);
		// 뷰 이름 지정
		mav.setViewName("board/replyList");
		// 뷰에 전달할 데이터 저장
		mav.addObject("list", list);
		mav.addObject("replyPagination", replyPagination);
		// replyList.jsp로 포워딩
		return mav;
	}

	// 3. 댓글 상세보기
	// /reply/detail/1 => 1번 댓글의 상세화면 리턴
	// /reply/detail/2 => 2번 댓글의 상세화면 리턴
	// @PathVariable : url에 입력될 변수값 지정
	@RequestMapping(value = "/detail/{rno}", method = RequestMethod.GET)
	public ModelAndView replyDetail(@PathVariable("rno") Integer rno, ModelAndView mav) {
		ReplyVO vo = replyService.detail(rno);
		// 뷰이름 지정
		mav.setViewName("board/replyDetail");
		// 뷰에 전달할 데이터 지정
		mav.addObject("vo", vo);
		// replyDetail.jsp로 포워딩
		return mav;
	}

	// 4. 댓글 수정 처리 - PUT: 전체 수정, PATCH: 일부 수정
	// RequestMethod 를 여러 방식으로 설정할 경우 {}안에 작성
	@RequestMapping(value = "/update/{rno}", method = { RequestMethod.PUT, RequestMethod.PATCH })
	public ResponseEntity<String> replyUpdate(@PathVariable("rno") Integer rno, @RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null;
		try {
			vo.setRno(rno);
			replyService.update(vo);
			// 댓글 수정이 성공하면 성공 상태메시지 저장
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			// 댓글 수정이 실패하면 실패 상태메시지 저장
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// 수정처리 HTTP 상태 메시지 리턴
		return entity;
	}

	// 5. 댓글 삭제 처리
	@RequestMapping(value = "/delete/{rno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> replyDelete(@PathVariable("rno") Integer rno) {
		ResponseEntity<String> entity = null;
		try {
			replyService.delete(rno);
			// 댓글 삭제가 성공하면 성공 상태메시지 저장
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			// 댓글 삭제가 실패하면 실패 상태메시지 저장
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// 삭제 처리 HTTP 상태 메시지 리턴
		return entity;
	}

}
