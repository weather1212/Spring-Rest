package com.hoseong.spring.dao.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<ReplyVO> list(Integer bno, int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("start", start);
		map.put("end", end);

		return sqlSession.selectList("replyMapper.listReply", map);
	}

	// 댓글 입력
	@Override
	public void write(ReplyVO vo) {
		sqlSession.insert("replyMapper.writeReply", vo);
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

	// 댓글 개수
	@Override
	public int count(int bno) {
		return sqlSession.selectOne("replyMapper.countReply", bno);
	}

}
