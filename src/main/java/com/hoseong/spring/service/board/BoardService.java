package com.hoseong.spring.service.board;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.hoseong.spring.vo.board.BoardVO;

public interface BoardService {

	// 게시글 작성
	public void writeBoard(BoardVO boardVO) throws Exception;

	// 게시글 상세보기
	public BoardVO readBoard(int bno) throws Exception;

	// 게시글 수정
	public void updateBoard(BoardVO boardVO) throws Exception;

	// 게시글 삭제
	public void deleteBoard(int bno) throws Exception;

	// 게시글 전체 목록
	public List<BoardVO> listAll() throws Exception;

	// 게시글 조회수
	public void increaseViewcnt(int bno, HttpSession session) throws Exception;

}
