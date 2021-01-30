package com.hoseong.spring.dao.board;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hoseong.spring.vo.board.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

	@Inject
	SqlSession sqlSession;

	// 댓글 목록
	@Override
	public List<ReplyVO> list(Integer bno) {
		return sqlSession.selectList("replyMapper.listReply", bno);
	}

	// 댓글 입력
	@Override
	public void write(ReplyVO vo) {
		sqlSession.insert("replyMapper.write", vo);
	}

	// 댓글 수정
	@Override
	public void update(ReplyVO vo) {
		sqlSession.update("replyMaper.update", vo);
	}

	// 댓글 삭제
	@Override
	public void delete(Integer rno) {
		sqlSession.delete("replyMapper.delete", rno);
	}

}
