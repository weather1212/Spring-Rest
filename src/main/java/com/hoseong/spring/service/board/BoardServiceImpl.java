package com.hoseong.spring.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.hoseong.spring.dao.board.BoardDAO;
import com.hoseong.spring.vo.board.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO boardDAO;

	// 게시글 작성
	@Override
	public void writeBoard(BoardVO boardVO) throws Exception {

		String title = boardVO.getTitle();
		String content = boardVO.getContent();
		String writer = boardVO.getWriter();

		// *태그문자 처리 (< ==> &lt; > ==> &gt;)
		// replace(A, B) A를 B로 변경
		title = title.replace("<", "&lt;");
		title = title.replace("<", "&gt;");
		writer = writer.replace("<", "&lt;");
		writer = writer.replace("<", "&gt;");
		// *공백문자 처리
		title = title.replace("  ", "&nbsp;&nbsp;");
		writer = writer.replace("  ", "&nbsp;&nbsp;");
		// *줄바꿈 문자처리
		content = content.replace("\n", "<br>");

		boardVO.setTitle(title);
		boardVO.setContent(content);
		boardVO.setWriter(writer);

		boardDAO.writeBoard(boardVO);
	}

	// 게시글 상세보기
	@Override
	public BoardVO readBoard(int bno) throws Exception {
		return boardDAO.readBoard(bno);
	}

	// 게시글 수정
	@Override
	public void updateBoard(BoardVO boardVO) throws Exception {
		boardDAO.updateBoard(boardVO);
	}

	// 게시글 삭제
	@Override
	public void deleteBoard(int bno) throws Exception {
		boardDAO.deleteBoard(bno);
	}

	// 게시글 전체 목록
	@Override
	public List<BoardVO> listAll(String searchOption, String keyword) throws Exception {
		return boardDAO.listAll(searchOption, keyword);
	}

	// 게시글 레코드 개수
	@Override
	public int countArticle(String searchOption, String keyword) throws Exception {
		return boardDAO.countArticle(searchOption, keyword);
	}

	// 게시글 조회수 증가
	@Override
	public void increaseViewcnt(int bno, HttpSession session) throws Exception {
		long update_time = 0;
		// 세션에 저장된 조회시간 검색
		// 최초로 조회할 경우 세션에 저장된 값이 없기 떄문에 if문 실행 x
		if (session.getAttribute("update_time" + bno) != null) {
			// 세션에서 읽어오기
			update_time = (long) session.getAttribute("update_time_" + bno);
		}
		// 시스템의 현재시간을 current_time에 저장
		long current_time = System.currentTimeMillis();
		// 일정시간이 경과 후 조회수 증가 처리 24*60*60*1999(24시간)
		// 시스템현재시간 - 열람시간 > 일정시간(조회수 증가가 가능하도록 지정한 시간)
		if (current_time - update_time > 5 * 1000) {
			boardDAO.increaseViewcnt(bno);
			// 세션에 시간을 저장 : "update_time_"+bno는 다른변수와 중복되지 않게 명명한 것
			session.setAttribute("update_time_" + bno, current_time);

		}

	}

}
