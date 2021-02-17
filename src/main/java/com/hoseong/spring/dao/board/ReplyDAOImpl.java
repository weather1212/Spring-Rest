package com.hoseong.spring.dao.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoseong.spring.vo.board.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

	@Autowired
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
		sqlSession.update("replyMapper.updateReply", vo);
	}

	// 댓글 삭제
	@Override
	public void delete(Integer rno) {
		sqlSession.delete("replyMapper.deleteReply", rno);
	}

	// 댓글 개수
	@Override
	public int count(int bno) {
		return sqlSession.selectOne("replyMapper.countReply", bno);
	}

	// 댓글 상세보기
	@Override
	public ReplyVO detail(Integer rno) {
		return sqlSession.selectOne("replyMapper.detailReply", rno);
	}

}
