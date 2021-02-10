package com.hoseong.spring.dao.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		sqlSession.insert("boardMapper.write", boardVO);
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
	public List<BoardVO> listAll(int stard, int end, String searchOption, String keyword) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		// 검색옵션, 키워드 맵에 저장
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		
		//BETWEEN #{start}, #{end}에 입력될 값을 맵에 저장
		map.put("start", stard);
		map.put("end", end);

		return sqlSession.selectList("boardMapper.listAll", map);
	}

	// 게시글 레코드 개수 메소드 추가
	@Override
	public int countArticle(String searchOption, String keyword) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);

		return sqlSession.selectOne("boardMapper.countArticle", map);
	}

	// 게시글 조회수
	@Override
	public void increaseViewcnt(int bno) throws Exception {
		sqlSession.update("boardMapper.increaseViewcnt", bno);
	}

	// 파일 업로드
	@Override
	public void addAttach(String fullName) {
		sqlSession.insert("boardMapper.addAttach", fullName);
	}

}
