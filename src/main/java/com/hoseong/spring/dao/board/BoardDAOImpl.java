package com.hoseong.spring.dao.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hoseong.spring.vo.board.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Inject
	private SqlSession sqlSession;

	// 게시글 작성
	@Override
	public void writeBoard(BoardVO boardVO) throws Exception {
		sqlSession.insert("boardMapper.wirte", boardVO);
	}

	// 게시글 상세 조회
	@Override
	public BoardVO readBoard(int bno) throws Exception {
		return sqlSession.selectOne("boardMapper.detail", bno);
	}

	// 게시글 수정
	@Override
	public void updateBoard(BoardVO boardVO) throws Exception {
		sqlSession.update("boardMapper.update", boardVO);
	}

	// 게시글 삭제
	@Override
	public void deleteBoard(int bno) throws Exception {
		sqlSession.delete("boardMapper.delete", bno);
	}

	// 게시글 목록 조회
	@Override
	public List<BoardVO> listAll() throws Exception {
		return sqlSession.selectList("boardMapper.listAll");
	}

	// 게시글 조회수
	@Override
	public void increaseViewcnt(int bno) throws Exception {
		sqlSession.update("boardMapper.increaseViewcnt", bno);
	}

}
