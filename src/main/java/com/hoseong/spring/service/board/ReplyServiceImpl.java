package com.hoseong.spring.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.hoseong.spring.dao.board.ReplyDAO;
import com.hoseong.spring.vo.board.ReplyVO;

import oracle.net.aso.b;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Inject
	private ReplyDAO replyDAO;

	// 댓글 목록
	@Override
	public List<ReplyVO> list(Integer bno, int start, int end, HttpSession session) {
		List<ReplyVO> items = replyDAO.list(bno, start, end);
		// 세션에서 현재 사용자 id값 저장
		String userId = (String) session.getAttribute("userId");
		for (ReplyVO vo : items) {
			// 댓글 목록중에 비밀댓글이 있을 경우
			if (vo.getSecretReply().equals("y")) {
				if (userId == null) { // 비로그인 상태면 비밀댓글로 처리
					vo.setReplytext("비밀 댓글입니다.");
				} else {
					String writer = vo.getWriter(); // 게시물 작성자
					String replyer = vo.getReplyer(); // 댓글 작성자
					// 로그인한 사용자가 게시물, 댓글 작성자 둘 다 아니면 비밀댓글로 처리
					if (!userId.equals(writer) && !userId.equals(replyer)) {
						vo.setReplytext("비밀 댓글입니다.");
					}
				}
			}
		}
		return items;
	}

	// 댓글 작성
	@Override
	public void write(ReplyVO vo) {
		replyDAO.write(vo);
	}

	// 댓글 수정
	@Override
	public void update(ReplyVO vo) {
		replyDAO.update(vo);
	}

	// 댓글 삭제
	@Override
	public void delete(Integer rno) {
		replyDAO.delete(rno);
	}

	// 댓글 개수
	@Override
	public int count(int bno) {
		return replyDAO.count(bno);
	}

	// 댓글 상세보기
	@Override
	public ReplyVO detail(Integer rno) {
		return replyDAO.detail(rno);
	}

}
