package com.hoseong.spring.service.board;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hoseong.spring.dao.board.ReplyDAO;
import com.hoseong.spring.vo.board.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Inject
	private ReplyDAO replyDAO;
	
	// 댓글 목록
	@Override
	public List<ReplyVO> list(Integer bno) {
		return replyDAO.list(bno);
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

}
